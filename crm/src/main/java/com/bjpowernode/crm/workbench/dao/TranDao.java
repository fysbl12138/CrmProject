package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    int getTotal();

    List<Tran> selectPageList(Map<String, Object> map);

    Tran getDetailById(String id);

    int changeStage(Tran tran);

    int getEchartsTotal();

    List<Map<String, Object>> getEchartsPageList();
}
