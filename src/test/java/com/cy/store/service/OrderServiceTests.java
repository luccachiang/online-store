package com.cy.store.service;

import com.cy.store.entity.Order;
import com.cy.store.vo.CartVO;
import com.cy.store.vo.OrderVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderServiceTests {
    @Autowired
    private IOrderService iOrderService;

    @Test
    public void create(){
        Integer aid = 14;
        Integer[] cids = {1, 3, 5, 7};
        Integer uid = 10;
        String username = "admin";

        Order order = iOrderService.create(aid, cids, uid, username);
        System.err.println(order);
    }

    @Test
    public void getVOByUid(){
        List<OrderVO> list = iOrderService.getVOByUid(10);
        System.err.println("count=" + list.size());
        for (OrderVO item : list) {
            System.err.println(item);
        }
    }
}
