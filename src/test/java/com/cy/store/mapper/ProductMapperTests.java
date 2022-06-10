package com.cy.store.mapper;

import com.cy.store.entity.District;
import com.cy.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class ProductMapperTests {

    @Autowired
    private ProductMapper productMapper;
    /**
     * 单元测试方法
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须时public
     */
    @Test
    public void findHotList(){
        List<Product> list = productMapper.findHotList();
        System.err.println("count=" + list.size());
        for (Product product : list){
            System.err.println(product);
        }
    }

    @Test
    public void findById(){
        Product result = productMapper.findById(10000017);
        System.err.println(result);
    }

}
