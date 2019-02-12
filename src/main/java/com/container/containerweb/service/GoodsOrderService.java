package com.container.containerweb.service;

import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.constants.OrderStatus;
import com.container.containerweb.dao.*;
import com.container.containerweb.dto.QueryOrderDto;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsCollect;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.VendingMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsOrderService {

    @Resource
    private GoodsOrderDao goodsOrderDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private MachineDao machineDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private EntityManager entityManager;

    @Resource
    private GoodsCollectDao collectDao;

    public List<GoodsOrder> getList() {
        return goodsOrderDao.findAll();
    }

    public GoodsOrder getOrderByGoodsBarcode(String barcode) {
        GoodsOrder order = goodsOrderDao.findGoodsOrderByGoodsBarcode(barcode);
        if (order == null) {
            throw new NullPointerException();
        }
        return order;
    }

    public void completeOrder(String tradeNo) {
        GoodsOrder order = goodsOrderDao.findByOrderNo(tradeNo);
        if (order != null) {
            order.setPaymentTime(System.currentTimeMillis());
            order.setStatus(OrderStatus.PAID.getCode());
            goodsOrderDao.save(order);
        } else {
            throw new NullPointerException("未找到:" + tradeNo);
        }
    }

    public GoodsOrder addOrder(GoodsOrder order) {
        VendingMachine machine = machineDao.findBySerial(order.getMachineSerial());
        if (machine == null) {
            throw new IllegalArgumentException();
        }
        Goods goods = goodsDao.findByBarcodeAndIdx(order.getGoods().getBarcode(), order.getGoods().getIdx());
        if (goods != null) {
            order.setId(null);
            order.setCreateTime(System.currentTimeMillis());
            order.setGoods(goods);
            order.setPayment(goods.getGoodsDescription().getPrice());
            order.setOrderNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + goods.getVendingMachine().getId() + goods.getGoodsDescription().getId());
            order.setStatus(OrderStatus.UNPAID.getCode());
            return goodsOrderDao.save(order);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Page<GoodsOrder> getPage(QueryOrderDto dto) {
        Specification<GoodsOrder> specification = specificationOfQueryOrderDto(dto);
        return goodsOrderDao.findAll(specification, new PageRequest(dto.getPage() - 1, dto.getSize()));
    }

    private Specification<GoodsOrder> specificationOfQueryOrderDto(QueryOrderDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(dto.getMachineSerial())) {
                predicates.add(cb.equal(root.get("machineSerial").as(String.class), dto.getMachineSerial()));
            } else if (!StringUtils.isEmpty(dto.getMerchantId())) {
                List<VendingMachine> machines = machineDao.findByMerchantId(dto.getMerchantId());
                List<String> serials = machines.stream().map(VendingMachine::getSerial).collect(Collectors.toList());
                predicates.add(root.get("machineSerial").in(serials));
            }
            if (!StringUtils.isEmpty(dto.getFrom())) {
                predicates.add(cb.ge(root.get("paymentTime"), dto.getFrom()));
            }
            if (!StringUtils.isEmpty(dto.getTo())) {
                predicates.add(cb.le(root.get("paymentTime"), dto.getTo()));
            }
            if (!StringUtils.isEmpty(dto.getGoodsDescId())){
                predicates.add(cb.equal(root.get("goods").get("goodsDescription").get("id").as(Integer.class), dto.getGoodsDescId()));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    public GoodsOrder getOrderByOrderNo(String tradeNo) {
        return goodsOrderDao.findByOrderNo(tradeNo);
    }

    @Transactional
    public void finishOrder(GoodsOrder order) {
        Goods goods = order.getGoods();
        goods.setStatus(GoodsStatus.SOLD.getCode());
        goodsDao.save(goods);
        order.setPaymentTime(System.currentTimeMillis());
        order.setStatus(OrderStatus.PAID.getCode());

        collectDao.updateSoldGoodsAmount(goods.getBatchNo(), goods.getGoodsDescription().getDescription());

        List<GoodsCollect> collects = collectDao.findByDeliverBatchNo(goods.getBatchNo());
        int sum = 0;
        for (GoodsCollect collect : collects) {
            sum += collect.getSingleTotalAmount();
        }
        collectDao.updateTotalAmount(sum, goods.getBatchNo());

        goodsOrderDao.save(order);
    }

    public String totalProfit(QueryOrderDto dto) {
        StringBuilder sql = new StringBuilder("select sum(payment) from goods_order where status=1000 ");
        if (dto.getMerchantId() != null) {
            List<VendingMachine> machines = machineDao.findByMerchantId(dto.getMerchantId());
            List<String> serials = machines.stream().map(VendingMachine::getSerial).collect(Collectors.toList());
            sql.append("and machine_serial in (");
            for (String serial : serials) {
                sql.append(serial).append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(") ");
        } else if (dto.getMachineSerial() != null) {
            sql.append("and machine_serial=").append(dto.getMachineSerial());
        }
        if (dto.getFrom() != null && dto.getTo() != null) {
            sql.append(" and payment_time between ").append(dto.getFrom()).append(" and ").append(dto.getTo());
        }
        List list = entityManager.createNativeQuery(sql.toString()).getResultList();
        return list.get(0) == null ? "0" : list.get(0).toString();
    }
}
