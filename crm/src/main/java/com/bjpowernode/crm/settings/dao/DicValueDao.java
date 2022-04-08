package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.entity.DicValue;

import java.util.List;

/**
 * @author fangy
 * @date 2022-02-18 11:00
 */
public interface DicValueDao {
    List<DicValue> getByCode(String code);
}
