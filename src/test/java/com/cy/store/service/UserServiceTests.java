package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService iUserService;
    /**
     * 单元测试方法
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须时public
     */
    @Test
    public void reg()
    {
        try {
            User user = new User();
            user.setUsername("lower03");
            user.setPassword("123456");
            user.setGender(1);
            user.setPhone("17858802974");
            user.setEmail("lower@tedu.cn");
            user.setAvatar("xxxx");
            iUserService.reg(user);
            System.out.println("注册成功！");
        } catch (ServiceException e) {
            System.out.println("注册失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login()
    {
        try {
            String username = "lower03";
            String password = "123456";
            User user = iUserService.login(username, password);
            System.out.println("登陆成功！" + user);
        } catch (ServiceException e) {
            System.out.println("登陆失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePassword(){
        try {
            Integer uid = 9;
            String username = "lower02";
            String oldPassword = "654321";
            String newPassword = "123456";
            iUserService.changePassword(uid, username, oldPassword, newPassword);
            System.out.println("密码修改成功！");
        } catch (ServiceException e) {
            System.out.println("密码修改失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        Integer uid = 10;
        System.err.println(iUserService.getByUid(uid));
    }

    @Test
    public void changeInfo() {
        Integer uid = 10;
        String username = "admin03";
        User user = new User();
        user.setPhone("10086");
        user.setEmail("admin03@qq.com");
        user.setGender(0);
        iUserService.changeInfo(uid, username, user);
        System.err.println("OK");
    }

    @Test
    public void changeAvatar(){
        Integer uid = 10;
        String username = "scu";
        String avatar = "/upload/jgq.jpg";
        iUserService.changeAvatar(uid, avatar, username);
    }
}
