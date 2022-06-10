package com.cy.store.controller;

import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    private ICartService iCartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session){
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 调用业务对象执行添加到购物车
        iCartService.addToCart(uid, pid, amount, username);

        return new JsonResult<Void>(OK);
    }

    @GetMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行查询数据
        List<CartVO> data = iCartService.getVOByUid(uid);
        // 返回成功与数据
        return new JsonResult<List<CartVO>>(OK, data);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        Integer data = iCartService.addNum(cid, uid, username);

        return new JsonResult<Integer>(OK, data);
    }

    @RequestMapping("{cid}/num/subtract")
    public JsonResult<Integer> subtNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        Integer data = iCartService.subtNum(cid, uid, username);

        return new JsonResult<>(OK, data);
    }

    @GetMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVO> data = iCartService.getVOByCids(uid, cids);

        return new JsonResult<>(OK, data);
    }

    @RequestMapping("{cid}/delete")
    public JsonResult<Void> deleteByCid(@PathVariable("cid") Integer cid, HttpSession session){
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);

        // 调用业务对象执行删除操作
        iCartService.deleteByCid(cid, uid);

        return new JsonResult<Void>(OK);
    }
}
