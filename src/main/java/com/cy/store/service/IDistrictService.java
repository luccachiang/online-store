package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

/** 处理省/市/区数据的业务层接口 */
public interface IDistrictService {
    /**
     * 获取全国的所有省、某省的所有市、某市的所有区
     * @param parent 父级代号，获取区时使用市的代号，获取市时使用省代号，获取省时使用“86”
     * @return 全国所有省、某省的所有市、某市的所有区
     */
    List<District> getByParent(String parent);

    /**
     * 根据省/市/区的行政代号获取省/市/区的名称
     * @param code 行政代号
     * @return 匹配的名称，没有匹配则返回null
     */
    String getNameByCode(String code);
}
