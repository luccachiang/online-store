package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/** 处理用户数据的业务层实现类 */
@Service//将当前类的对象交给spring来管理，自动创建对象和维护
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;


    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        // 在进行网络数据的传输时，为了尽量避免无效数据的传递，可以将无效数据设置为null
        // 可以节省流量，同时提升性能
        for (District d: list) {
            d.setParent(null);
            d.setId(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
