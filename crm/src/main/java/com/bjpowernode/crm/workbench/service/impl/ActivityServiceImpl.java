package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.entity.Activity;
import com.bjpowernode.crm.workbench.entity.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-09 11:11
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;
    @Resource
    private ActivityRemarkDao activityRemarkDao;
    @Resource
    private UserDao dao;

    @Override
    public Boolean save(Activity activity) {
        boolean flag = true;
        int count = activityDao.save(activity);

        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> selectByElenment(Map<String, Object> map) {
        int total = activityDao.getTotal();

        List<Activity> vo = activityDao.selectByElenment(map);

        PaginationVO<Activity> dataList = new PaginationVO<>();
        dataList.setTotal(total);
        dataList.setDataList(vo);

        return dataList;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //需要查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数
        int count2 = activityRemarkDao.deleteByAids(ids);
        //删除市场活动
        if (count1 != count2){
            flag = false;
        }

        int count3 = activityDao.delete(ids);
        if(count3 != ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> updata(String id) {
        //取userList
        List<User> uList = dao.selectAllUser();
        //取activity记录
        Activity a = activityDao.getActivityById(id);

        Map<String , Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);

        return map;
    }

    @Override
    public Boolean updataSub(Activity activity) {
        boolean flag1 = true;
        int count = activityDao.updataSub(activity);
        if(count != 1){
            flag1 = false;
        }
        return flag1;
    }

    @Override
    public Activity detailgetById(String id) {

        Activity activity = activityDao.getDetailById(id);

        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> activityRemark = activityRemarkDao.getRemarkListByAid(activityId);
        return activityRemark;
    }

    @Override
    public boolean deleteRemarkByAid(String activityId) {
        boolean flag = true;
        int count = activityRemarkDao.deleteRemarkByAid(activityId);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        int count = activityRemarkDao.saveRemark(activityRemark);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {

        boolean flag = true;
        int count = activityRemarkDao.updateRemark(ar);
        if(count != 1){
            flag = false;
        }
        return flag;

    }

    @Override
    public List<Activity> getActivityAndClue(String id) {

        List<Activity> activity = activityDao.getActivityAndClue(id);

        return activity;
    }

    @Override
    public List<Activity> getClueActivity(String clueId, String aname) {

        List<Activity> activities = activityDao.getClueActivity(clueId,aname);

        return activities;
    }
}
