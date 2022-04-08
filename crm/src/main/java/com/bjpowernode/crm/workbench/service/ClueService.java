package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.workbench.entity.Activity;
import com.bjpowernode.crm.workbench.entity.ActivityRemark;
import com.bjpowernode.crm.workbench.entity.Clue;
import com.bjpowernode.crm.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-09 11:11
 */
public interface ClueService {

    Boolean save(Clue clue);

    PaginationVO<Clue> selectPageListByElenment(Map<String, Object> map);

    Clue selectById(String id);

    boolean deleteActivityAndClueById(String id);

    boolean bundActivityAndClue(String cid, String[] aids);

    List<Activity> getClueActivity(String aname);

    boolean convert(String clueId, Tran t, String createBy);
}
