package com.cy.store.service;

import com.cy.store.entity.User;

/** 处理用户数据的业务层接口 */
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登陆成功之后的User对象，如果登陆失败返回null
     */
    User login(String username, String password);

    /**
     * 修改密码
     * @param uid 当前用户的id
     * @param username 用户名
     * @param oldpassword 原始密码
     * @param newpassword 修改后的新密码
     */
    void changePassword(Integer uid, String username,
                        String oldpassword, String newpassword);

    /**
     * 获取当前登录用户的信息
     * @param uid 当前登录用户的id
     * @return 当前登录用户的信息
     */
    User getByUid(Integer uid);

    /**
     * 修改用户的资料
     * @param uid 当前登录用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新数据（只包含个人资料页上的三个数据，uid和username可以从session获取）
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户的头像
     * @param uid 当前登录用户的id
     * @param avatar 新头像的路径
     * @param username 当前登录用户的用户名
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
