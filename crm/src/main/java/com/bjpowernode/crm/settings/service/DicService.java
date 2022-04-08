package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.entity.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-18 11:03
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
