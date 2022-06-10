package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/** 处理用户数据的业务层实现类 */
@Service//将当前类的对象交给spring来管理，自动创建对象和维护
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    // 在添加用户的收获地址的业务层依赖于IDistrictService的业务层接口
    @Autowired
    private IDistrictService iDistrictService;

    @Value("${user.address.max-count}")
    private int maxCount;


    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        // 根据参数uid调用addressMapper的countByUid()方法，统计当前用户的收货地址数据的数量
        // 判断数量是否达到上限值
        // 是：抛出AddressCountLimitException
        Integer count = addressMapper.countByUid(uid);
        if (count > maxCount){
            throw new AddressCountLimitException("用户创建地址已达上限(" + maxCount + ")");
        }

        // 对address对象中的数据进行补全，省市区的相关数据
        // code是从前端传过来的数据，所以可以使用address中的get方法获取
        String provinceName = iDistrictService.getNameByCode(address.getProvinceCode());
        String cityName = iDistrictService.getNameByCode(address.getCityCode());
        String areaName = iDistrictService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        // 补全数据：将参数uid封装到参数address中
        address.setUid(uid);
        // 补全数据：根据以上统计的数量，得到正确的isDefault值(是否默认：0-不默认，1-默认)，并封装
        if (count != 0){
            address.setIsDefault(0);// 不默认
        } else {
            address.setIsDefault(1);// 默认
        }
        // 补全数据：4项日志
        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);

        // 调用addressMapper的insert()方法插入收货地址数据，并获取返回的受影响行数
        Integer rows = addressMapper.insert(address);
        // 判断受影响行数是否不为1
        // 是：抛出InsertException
        if (rows != 1){
            throw new InsertException("插入收货地址时出现未知的错误");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address: list) {
            // 把前端不展示的信息设置为null，减轻传输数据量
//            address.setAid(null);
//            address.setUid(null);
            // 记录一个bug，这里uid和aid不可以设置成null
            // 尤其是aid，因为在设置默认地址的时候，需要用到list中的aid
            // 详见address.html，用了list中的aid填充占位符
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Transactional
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据参数aid，调用addressMapper中的findByAid()查询收货地址数据
        Address resutlt = addressMapper.findByAid(aid);
        // 判断查询结果是否为null
        // 是：抛出AddressNotFoundException
        if (resutlt == null){
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
        // 是：抛出AccessDeniedException：非法访问
        if (!resutlt.getUid().equals(uid)){
            throw new AccessDeniedException("非法访问");
        }

        // 调用addressMapepr的updateNonDefaultByUid()将该用户的所有收货地址全部设置为非默认，并获取返回的受影响的行数
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        // 判断受影响的行数是否小于1(不大于0)
        // 是：抛出UpdateException
        if (rows < 1){
            throw new UpdateException("设置默认收获地址时出现未知错误，1");
        }

        // 调用addressMapepr的updateDefaultByAid()将指定aid的收货地址设置为默认，并获取返回的受影响的行数
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        // 判断受影响的行数是否不为1
        // 是：抛出UpdateException
        if (rows != 1){
            throw new UpdateException("设置默认收货地址时出现未知错误，2");
        }
    }

    @Transactional
    @Override
    public void delete(Integer aid, Integer uid, String username) {
        // 根据参数aid，调用findByAid()查询收货地址数据
        Address result = addressMapper.findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是，抛出异常
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在！");
        }

        // 判断查询结果中的uid与参数uid是否不一致（使用equals()判断）
        if (!result.getUid().equals(uid)) {
            // 是，抛出没有权限访问的异常
            throw new AccessDeniedException("非法访问！");
        }

        // 根据参数aid，调用deleteByAid()执行删除
        Integer rows1 = addressMapper.deleteByAid(aid);
        if (rows1 != 1){
            throw new DeleteException("删除收货地址数据时出现未知错误，请联系系统管理员");
        }

        // 判断查询结果中的isDefault是否为0，为0表示该地址不是默认地址，
        // 不需要再考虑默认地址修改的操作
        if (result.getIsDefault().equals(0)){
            return;
        }

        // 调用持久层的countByUid()统计目前还有多少收货地址
        Integer count = addressMapper.countByUid(uid);
        // 判断目前收货地址的数量是否为0，为0表示没有该用户没有地址，
        // 也不需要再考虑修改默认地址
        if (count == 0){
            return;
        }

        // 调用findLastModified()找出用户最近修改的收货地址数据
        Address lastModified = addressMapper.findLastModified(uid);
        // 从以上查询结果中找出aid属性值
        Integer lastModifiedAid = lastModified.getAid();
        // 调用持久层的updateDefaultByAid()方法执行设置默认收货地址，并获取返回的受影响行数
        Integer rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        // 判断受影响行数是否不为1
        if (rows2 != 1){
            // 是，抛出异常
            throw new UpdateException("更新收货地址时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        // 根据收货地址数据id，查询收货地址详情
        Address address = addressMapper.findByAid(aid);

        if (address == null){
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法访问");
        }

        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }


}
