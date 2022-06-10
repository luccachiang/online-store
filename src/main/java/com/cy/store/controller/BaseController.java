package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制层类的基类 */
public class BaseController {
    // 操作成功的状态码
    public static final int OK = 200;

    /** @ExceptionHandler用于统一处理方法抛出的异常 */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e)
    {
        JsonResult<Void> result = new JsonResult<Void>(e);
        if(e instanceof UsernameDuplicateException) {
            result.setState(4000);
            result.setMessage("用户名被占用的异常");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户名不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户密码不匹配的异常");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户收货地址超出上限的异常");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户收货地址未找到");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("没有访问权限");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("商品信息未找到");
        } else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车信息未找到");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生未知的异常");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除数据时产生未知的异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("上传数据为空");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("上传文件大小超出");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("上传文件类型错误");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("上传文件状态异常");
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("上传文件异常");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户的uid
     */
    protected final Integer getUidFromSession(HttpSession session)
    {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     * @param session session对象
     * @return 当前登录用户的用户名
     *
     * 在实现类中重写了父类的toString方法？可能不是句柄信息输出
     */
    protected final String getUsernameFromSession(HttpSession session)
    {
        return session.getAttribute("username").toString();
    }
}
