package com.cy.store.controller;

import com.cy.store.controller.BaseController;
import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService iDistrictService;

    // districts开头的请求都被拦截到这个方法
    // RequestMapping不能省略，否则getByParent变成一个普通方法
    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = iDistrictService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}
