package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

/** 处理商品搜索的持久层接口 */
public interface SearchMapper {
    /**
     * 根据关键字查询商品
     * @return 查询到的商品的集合
     */
    List<Product> findProductList(String key);
}
