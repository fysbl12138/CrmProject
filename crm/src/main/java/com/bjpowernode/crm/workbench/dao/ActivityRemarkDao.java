package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.entity.ActivityRemark;

import java.util.List;

/**
 * @author fangy
 * @date 2022-02-09 11:06
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemarkByAid(String activityId);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark ar);
}
