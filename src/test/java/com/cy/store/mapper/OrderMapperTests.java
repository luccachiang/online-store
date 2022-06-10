package com.cy.store.mapper;

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.vo.OrderVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder(){
        Order order = new Order();
        order.setUid(20);
        order.setRecvName("jgq");
        Integer rows = orderMapper.insertOrder(order);

        System.err.println("rows=" + rows);
    }

    @Test
    public void insertOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(2);
        orderItem.setTitle("你的背包");
        Integer rows = orderMapper.insertOrderItem(orderItem);

        System.err.println("rows=" + rows);
    }

    @Test
    public void findVOByUid() {
        List<OrderVO> list = orderMapper.findVOByUid(10);
        System.err.println(list.size());
//        System.out.println(list);
        for (OrderVO orderVO : list){
            System.err.println(orderVO);
        }
    }
}
