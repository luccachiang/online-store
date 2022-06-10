package com.cy.store.service.impl;

import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.mapper.SearchMapper;
import com.cy.store.service.IProductService;
import com.cy.store.service.ISearchService;
import com.cy.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 处理商品数据的业务层实现类 */
@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private SearchMapper searchMapper;


    @Override
    public List<Product> findProductList(String key) {
        List<Product> list = searchMapper.findProductList(key);
        for (Product product : list){
            product.setPriority(null);
            product.setCategoryId(null);
            product.setNum(null);
            product.setSellPoint(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }
}
