package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.entity.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int deleteActivityAndClueById(String id);

    int bundActivityAndClue(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}

