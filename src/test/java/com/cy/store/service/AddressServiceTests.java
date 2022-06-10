package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService iAddressService;
    /**
     * 单元测试方法
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须时public
     */

    @Test
    public void addNewAddress(){
        Integer uid = 20;
        String username = "admin";
        Address address = new Address();
        address.setName("lucca");
        address.setPhone("10086");
        address.setAddress("软件学院");
        iAddressService.addNewAddress(uid, username, address);
        System.out.println("OK");
    }

    @Test
    public void getByUid(){
        Integer uid = 10;
        List<Address> list = iAddressService.getByUid(uid);
        System.err.println(list);
    }

    @Test
    public void setDefault(){
        Integer aid = 8;
        Integer uid = 10;
        String username = "admin";
        iAddressService.setDefault(aid, uid, username);
        System.err.println("OK");
    }

    @Test
    public void delete(){
//        iAddressService.delete(9, 11, "admin");
        iAddressService.delete(7, 10, "admin");
//        iAddressService.delete(9, 10, "admin");
        System.err.println("OK");
    }
}
