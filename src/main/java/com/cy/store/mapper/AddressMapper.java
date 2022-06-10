package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/** 处理收货地址数据的持久层接口 */
public interface AddressMapper {
    /**
     * 插入收货地址的数据
     * @param address 用户的数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 统计某用户收货地址数据的数量
     * @param uid 用户的id
     * @return 该用户的收货地址数据的数量
     */
    Integer countByUid(Integer uid);

    /**
     * 查询某用户的收货地址列表数据
     * @param uid 收货地址归属的用户id
     * @return 该用户的收货地址列表数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 将某用户的所有收货地址设置为非默认地址
     * @param uid 收货地址归属的用户id
     * @return 受影响的行数
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * 将指定的收货地址设置为默认地址
     * @param aid 收货地址id
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货地址的aid值，查询收货地址详情
     * @param aid 收货地址id
     * @return 匹配的收货地址详情，没有匹配返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据收货地址的aid删除数据
     * @param aid 收货地址的aid
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 查询某用户最后修改的收货地址
     * @param uid 归属的用户uid
     * @return 该用户最后修改的收货地址，如果没有收货地址数据返回null
     */
    Address findLastModified(Integer uid);
}
