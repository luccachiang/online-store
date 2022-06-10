package com.cy.store.service;

import com.cy.store.vo.CartVO;

import java.util.List;

/** 处理购物车数据的业务层接口 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 将购物车中某商品的数量加1
     * @param cid 购物车的id
     * @param uid 当前登录用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 将购物车中某商品的数量减1
     * @param cid 购物车的id
     * @param uid 当前登录用户的id
     * @param username 当前登录的用户名
     * @return 减少成功后新的数量
     */
    Integer subtNum(Integer cid, Integer uid, String username);

    /**
     * 根据若干个购物车数据id查询详情的列表
     * @param uid 当前登录用户的id
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);

    /**
     * 根据购物车id删除购物车商品记录
     * @param cid 购物车id
     * @param uid 当前登录的用户id
     * @return 受影响的行数
     */
    Integer deleteByCid(Integer cid, Integer uid);
}
