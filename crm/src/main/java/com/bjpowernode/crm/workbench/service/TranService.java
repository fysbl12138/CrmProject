package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.workbench.entity.Clue;
import com.bjpowernode.crm.workbench.entity.Tran;
import com.bjpowernode.crm.workbench.entity.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-25 20:42
 */
public interface TranService {

    boolean save(Tran tran, String accountName);

    PaginationVO<Tran> selectPageListByElenment(Map<String, Object> map);

    Tran getDetailById(String id);

    List<TranHistory> getHistoryListById(String id);

    boolean changeStage(Tran tran);

    Map<String, Object> getEchartsPageList();

}
