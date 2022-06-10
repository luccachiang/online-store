package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须时public
     */
    @Test
    public void insert()
    {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByName()
    {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid()
    {
        Integer uid = 7;
        String password = "123";
        String modifiedUSer = "admin";
        Date modifiedTime = new Date();
        Integer rows = userMapper.updatePasswordByUid(uid,
                                    password, modifiedUSer, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUid()
    {
        Integer uid = 7;
        User result = userMapper.findByUid(uid);
        System.out.println(result);
    }

    @Test
    public void updateInfoByUid()
    {
        User user = new User();
        user.setUid(10);
        user.setPhone("+86 12345");
        user.setEmail("test03@qq.com");
        user.setGender(1);
        user.setModifiedUser("admin");
        user.setModifiedTime(new Date());
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(
                10, "/scu/scu.png", "admin", new Date());
    }
}
