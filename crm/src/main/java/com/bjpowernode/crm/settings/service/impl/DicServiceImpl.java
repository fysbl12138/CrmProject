package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.entity.DicType;
import com.bjpowernode.crm.settings.entity.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-18 11:03
 */
@Service
public class DicServiceImpl implements DicService {

    @Resource
    private DicTypeDao dicTypeDao;

    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String ,List<DicValue>> map = new HashMap<String ,List<DicValue>>();

        List<DicType> dtList  = dicTypeDao.getTypeList();

        for (DicType dt: dtList) {
            String code = dt.getCode();
            List<DicValue> dvList = dicValueDao.getByCode(code);

            map.put(code,dvList);
        }

        return map;
    }
}
