package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.entity.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-09 11:04
 */
public interface ActivityDao {

    int save(Activity activity);


    List<Activity> selectByElenment(Map<String, Object> map);

    int getTotal();

    int delete(String[] ids);

    Activity getActivityById(String id);

    int updataSub(Activity activity);

    Activity getDetailById(String id);

    List<Activity> getActivityAndClue(String id);

    List<Activity> getClueActivity(@Param("clueId") String clueId,@Param("aname") String aname);

    List<Activity> getActivityByName(@Param("aname")String aname);
}
