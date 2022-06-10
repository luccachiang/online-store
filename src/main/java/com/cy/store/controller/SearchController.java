package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.service.ISearchService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController extends BaseController{
    @Autowired
    private ISearchService iSearchService;

    @RequestMapping("{key}/list")
    public JsonResult<List<Product>> getProductList(@PathVariable("key") String key){
        System.err.println(key);
        List<Product> data = iSearchService.findProductList(key);
        System.err.println(data);
        return new JsonResult<List<Product>>(OK, data);
    }

}
