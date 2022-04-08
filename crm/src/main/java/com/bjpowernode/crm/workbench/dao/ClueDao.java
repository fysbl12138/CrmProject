package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.entity.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    int getTotal();

    List<Clue> selectPageList(Map<String, Object> map);

    Clue selectById(String id);

    Clue getClueById(String clueId);

    int detele(String clueId);
}
