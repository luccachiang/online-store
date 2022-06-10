package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;
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
        Address address = new Address();
        address.setUid(18);
        address.setName("jgq");
        address.setPhone("12345");
        address.setAddress("四川大学");
        Integer rows = addressMapper.insert(address);
        System.out.println("rows=" + rows);
    }

    @Test
    public void countByUid(){
        Integer uid = 18;
        Integer count = addressMapper.countByUid(uid);
        System.out.println("count=" + count);
    }

    @Test
    public void findByUid(){
        Integer uid = 10;
        List<Address> list = addressMapper.findByUid(uid);
        System.err.println("count=" + list.size());
        System.err.println(list);
    }

    @Test
    public void updateNonDefaultByUid(){
        System.err.println("rows=" + addressMapper.updateNonDefaultByUid(10));
    }

    @Test
    public void updateDefaultByAid(){
        Integer aid = 1;
        String modifiedUser = "admin";
        Date modifiedTime = new Date();
        System.err.println("rows=" + addressMapper.updateDefaultByAid(aid, modifiedUser, modifiedTime));
    }

    @Test
    public void findByAid(){
        Integer aid = 1;
        System.err.println(addressMapper.findByAid(aid));
    }

    @Test
    public void deleteByAid(){
        System.err.println("rows=" + addressMapper.deleteByAid(6));
    }

    @Test
    public void findLastModified(){
        System.err.println(addressMapper.findLastModified(10));
    }
}
