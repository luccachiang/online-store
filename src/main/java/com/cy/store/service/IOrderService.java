package com.cy.store.service;

import com.cy.store.entity.Order;
import com.cy.store.vo.OrderVO;

import java.util.List;

/** 处理订单和订单数据的业务层接口 */
public interface IOrderService {
    /**
     * 创建订单
     * @param aid 收货地址的id
     * @param cids 即将购买的商品数据在购物车表中的id
     * @param uid 当前登录用户的id
     * @param username 当前登录用户的用户名
     * @return 成功创建的订单数据
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);

    /**
     * 查询某用户的订单数据
     * @param uid 用户id
     * @return 该用户的订单数据的列表
     */
    List<OrderVO> getVOByUid(Integer uid);
}
