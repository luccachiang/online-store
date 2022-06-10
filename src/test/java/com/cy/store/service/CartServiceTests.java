package com.cy.store.service;

import com.cy.store.entity.Product;
import com.cy.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


// @SpringBootTest：表示标注当前的类是一个测试类，不会随着项目一起打包发送
@SpringBootTest
//@RunWith(SpringRunner.class)
public class CartServiceTests {

    @Autowired
    private ICartService iCartService;
    /**
     * 单元测试方法
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须时public
     */

    @Test
    public void addToCart(){
        iCartService.addToCart(2, 10000007, 1, "jgq");
        System.err.println("OK");
    }

    @Test
    public void getVOByUid() {
        List<CartVO> list = iCartService.getVOByUid(10);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void addNum(){
        Integer num = iCartService.addNum(5, 10, "admin");
        System.err.println("OK. New num=" + num);
    }

    @Test
    public void subtNum(){
        Integer num = iCartService.subtNum(2, 2, "admin");
        System.err.println("OK. New num=" + num);
    }

    @Test
    public void getVOByCids(){
        Integer[] cids = {1, 3, 5, 7, 9};
        Integer uid = 10;
        List<CartVO> list = iCartService.getVOByCids(uid, cids);
        System.err.println("count=" + list.size());
        for (CartVO item : list){
            System.err.println(item);
        }
    }

    @Test
    public void deleteByCid(){
        System.err.println("rows=" + iCartService.deleteByCid(2, 2));
    }
}
