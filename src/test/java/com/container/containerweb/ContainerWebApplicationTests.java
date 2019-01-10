package com.container.containerweb;

import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.service.GoodsOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ContainerWebApplicationTests {

    @Resource
    private GoodsOrderService goodsOrderService;

    @Test
    public void testFinishOrder(){
        GoodsOrder order = goodsOrderService.getOrderByOrderNo("2019011022594111");
        goodsOrderService.finishOrder(order);
    }
}
