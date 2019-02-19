package com.container.containerweb.service;

import com.container.containerweb.dao.GoodsDescDao;
import com.container.containerweb.dao.MerchantDao;
import com.container.containerweb.model.biz.GoodsDescription;
import com.container.containerweb.model.biz.Merchant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@Service
public class GoodsDescService {

    @Resource
    private GoodsDescDao goodsDescDao;

    @Resource
    private MerchantDao merchantDao;

    @Value("${web.upload.img}")
    private String imgPath;

    public List<GoodsDescription> getList() {
        return goodsDescDao.findAll();
    }

    public void addGoodsDesc(MultipartFile file, String desc, String price, String barcode, Merchant merchant) throws IOException {
        GoodsDescription description = new GoodsDescription();
        description.setDescription(desc);
        description.setPrice(Integer.valueOf(price));
        description.setMerchant(merchant);
        description.setBarcode(barcode);
        String hash = DigestUtils.md5DigestAsHex(file.getBytes());
        description.setImageHash(hash);
        goodsDescDao.save(description);
        try (OutputStream os = new FileOutputStream(imgPath + "/" + hash)) {
            os.write(file.getBytes());
        }
    }

    public void deleteById(Integer id) {
        goodsDescDao.delete(id);
    }

    public void updateGoodsDesc(MultipartFile file, String desc, String price, String id, String barcode) throws IOException {
        GoodsDescription description = goodsDescDao.findOne(Integer.valueOf(id));
        if (description != null){
            description.setPrice(Integer.valueOf(price));
            description.setDescription(desc);
            description.setBarcode(barcode);
            if (file != null){
                String hash = DigestUtils.md5DigestAsHex(file.getBytes());
                try (OutputStream os = new FileOutputStream(imgPath + "/" + hash)) {
                    os.write(file.getBytes());
                }
                description.setImageHash(hash);
            }
            goodsDescDao.save(description);
        }
    }

    public List<GoodsDescription> getListByMerchant(Integer merchantId) {
        if (merchantId == null){
            return Collections.emptyList();
        }
        Merchant merchant = merchantDao.findOne(merchantId);
        if (merchant == null){
            return Collections.emptyList();
        }
        GoodsDescription d = new GoodsDescription();
        d.setMerchant(merchant);
        return goodsDescDao.findAll(Example.of(d));
    }
}
