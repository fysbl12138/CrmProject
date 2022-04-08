package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.workbench.entity.Activity;
import com.bjpowernode.crm.workbench.entity.ActivityRemark;
import com.bjpowernode.crm.workbench.entity.Clue;

import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-09 11:11
 */
public interface ActivityService {

    Boolean save(Activity activity);


    PaginationVO<Activity> selectByElenment(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> updata(String id);

    Boolean updataSub(Activity activity);

    Activity detailgetById(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemarkByAid(String activityId);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark ar);

    List<Activity> getActivityAndClue(String id);

    List<Activity> getClueActivity(String clueId, String aname);
}
