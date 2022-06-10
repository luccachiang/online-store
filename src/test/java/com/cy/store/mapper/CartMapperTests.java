package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(2);
        cart.setNum(3);
        cart.setPrice(4L);

        System.err.println("rows=" + cartMapper.insert(cart));
    }

    @Test
    public void updateNumByCid(){
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "admin";
        Date modifiedTime = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);

        System.err.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid(){
        Cart result = cartMapper.findByUidAndPid(1, 2);

        System.err.println(result);
    }

    @Test
    public void findVOByUid() {
        List<CartVO> list = cartMapper.findVOByUid(10);
        System.err.println(list);
    }

    @Test
    public void findByCid(){
        System.err.println(cartMapper.findByCid(2));
    }

    @Test
    public void findVOByCids(){
        Integer[] cids = {1, 2, 8, 10};
        List<CartVO> list = cartMapper.findVOByCids(cids);
        System.err.println("count=" + list.size());
        for (CartVO item : list){
            System.err.println(item);
        }
    }

    @Test
    public void deleteByCid(){
        System.err.println("rows=" + cartMapper.deleteByCid(1));
    }
}
