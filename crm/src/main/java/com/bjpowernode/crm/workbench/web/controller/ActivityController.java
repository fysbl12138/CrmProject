package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.entity.Activity;
import com.bjpowernode.crm.workbench.entity.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author fangy
 * @date 2022-02-05 17:20
 */
@Controller
@RequestMapping("workbench/activity")
public class ActivityController {

   @Resource
    private UserService userService;

   @Resource
   private ActivityService activityService;



    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入到填充用户操作");


        List<User> users = userService.selectAllUser();

//        PrintJson.printJsonObj(response,users);

        return users;

    }
    @RequestMapping("/save.do")
    @ResponseBody
    public boolean save(HttpServletRequest request , Activity activity){
        System.out.println("进入到市场活动控制器操作");

        String id = UUIDUtil.getUUID();
        String createTime =DateTimeUtil.getSysTime();
        String createBy =((User)request.getSession().getAttribute("user")).getName();

        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        Boolean flag = activityService.save(activity);

        return flag;
    }

//    @RequestMapping("/save.do")
//    public void save(HttpServletRequest request ,HttpServletResponse response){
//        System.out.println("进入到市场活动控制器操作");
//
//        String id = UUIDUtil.getUUID();
//        String owner =request.getParameter("owner");
//        String name =request.getParameter("name");
//        String startDate =request.getParameter("startDate");
//        String endDate  =request.getParameter("endDate");
//        String cost =request.getParameter("cost");
//        String description =request.getParameter("description");
//        String createTime =DateTimeUtil.getSysTime();
//        String createBy =((User)request.getSession().getAttribute("user")).getName();
//
//        Activity activity = new Activity();
//        activity.setId(id);
//        activity.setOwner(owner);
//        activity.setName(name);
//        activity.setStartDate(startDate);
//        activity.setEndDate(endDate);
//        activity.setCost(cost);
//        activity.setDescription(description);
//        activity.setCreateTime(createTime);
//        activity.setCreateBy(createBy);
//
//
//        Boolean flag = activityService.save(activity);
//        PrintJson.printJsonFlag(response,flag);
//    }


    @RequestMapping("/pageList.do")
    public void pageList(HttpServletRequest request ,HttpServletResponse response){
        System.out.println("进入到市场活动查询控制器操作");

        //页数
        int pageNo =Integer.valueOf(request.getParameter("pageNo"));
        //每页展示记录数
        int pageSize =Integer.valueOf(request.getParameter("pageSize"));
        String owner =request.getParameter("owner");
        String name =request.getParameter("name");
        String startDate =request.getParameter("startDate");
        String endDate  =request.getParameter("endDate");

        //计算略过记录数   limit （1,2）
        int skipCount = (pageNo-1) * pageSize;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);



        PaginationVO<Activity> vo = activityService.selectByElenment(map);

        PrintJson.printJsonObj(response,vo);

    }
    @RequestMapping("/delete.do")
    public void delete(HttpServletRequest request ,HttpServletResponse response){
        System.out.println("进入到市场活动删除控制器操作");

        String ids[] = request.getParameterValues("id");

        boolean falg = activityService.delete(ids);

        PrintJson.printJsonFlag(response,falg);
    }

    @RequestMapping("/updata.do")
    public void updata(HttpServletRequest request ,HttpServletResponse response){
        System.out.println("进入到市场活动修改控制器操作");

        String id = request.getParameter("id");

        Map<String ,Object> map = activityService.updata(id);

        PrintJson.printJsonObj(response,map);
    }

    @RequestMapping("/updataSubmit.do")
    public void updataSubmit(HttpServletRequest request ,HttpServletResponse response){
        System.out.println("进入到市场活动修改提交控制器操作");

        String id = request.getParameter("id");
        String owner =request.getParameter("owner");
        String name =request.getParameter("name");
        String startDate =request.getParameter("startDate");
        String endDate  =request.getParameter("endDate");
        String cost =request.getParameter("cost");
        String description =request.getParameter("description");
        String editTime =DateTimeUtil.getSysTime();
        String editBy =((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);


        Boolean flag = activityService.updataSub(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/detail.do")
    public void ActivityDetail(HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动detail控制器操作");

        String id = request.getParameter("id");


        Activity activity = activityService.detailgetById(id);

        request.setAttribute("a",activity);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动备注控制器操作");

        String activityId = request.getParameter("activityId");


        List<ActivityRemark> activityRemark = activityService.getRemarkListByAid(activityId);

        return activityRemark;
    }

    @RequestMapping("/deleteRemarkByAid.do")
    public void deleteRemarkByAid(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动备注控制器操作");

        String activityId = request.getParameter("activityId");

        boolean flag = activityService.deleteRemarkByAid(activityId);

        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Map<String, Object> saveRemark(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动备注控制器操作");

        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime =DateTimeUtil.getSysTime();
        String createBy =((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();

        ar.setActivityId(activityId);
        ar.setId(id);
        ar.setEditFlag(editFlag);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setNoteContent(noteContent);

        boolean flag = activityService.saveRemark(ar);

        Map<String ,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);

        return map;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String ,Object> updateRemark(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动修改备注控制器操作");

        String Id = request.getParameter("Id");
        String noteContent = request.getParameter("noteContent");
        String editTime =DateTimeUtil.getSysTime();
        String editBy =((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();

        ar.setId(Id);
        ar.setEditFlag(editFlag);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ar.setNoteContent(noteContent);

        boolean flag = activityService.updateRemark(ar);

        Map<String ,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);

        return map;
    }
}
