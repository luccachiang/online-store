package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/** 用户模块的持久层接口*/
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid来修改用户密码
     * @param uid 用户的uid
     * @param password 用户输入的新密码
     * @param modifiedUser 修改的执行者
     * @param modifiedTime 修改数据的时间
     * @return 返回值为受影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password,
                                String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户的id
     * @return 如果找到数据则返回User对象，没找到则返回null
     */
    User findByUid(Integer uid);

    /**
     * @Param("SQL映射文件中#{}占位符的变量名")：解决的问题，当SQL语句的占位符
     * 和映射的接口方法参数名不一致时，需要将某个参数强行注入到某个占位符变量上时，
     * 可以使用@Param这个注解来标注映射关系
     *
     * 根据用户的uid更新用户资料
     * @param user 封装了用户id和新个人资料的对象
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据用户的uid来修改用户的头像
     * @param uid 用户的id
     * @param avatar 用户的头像地址
     * @param modifiedUser 修改的用户
     * @param modifiedTime 修改的时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(
            @Param("uid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
}
