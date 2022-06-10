package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class DistrictServiceTests {

    @Autowired
    private IDistrictService iDistrictService;
    /**
     * 单元测试方法
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须时public
     */

    @Test
    public void getByParent(){
        // 86表示中国，查询所有省
        List<District> list = iDistrictService.getByParent("86");
        for (District d:list) {
            System.err.println(d);
        }
    }

    @Test
    public void getNameByCode(){
        String code = "430000";
        System.err.println(iDistrictService.getNameByCode(code));
    }
}
