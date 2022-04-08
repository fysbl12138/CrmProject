package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.entity.Activity;
import com.bjpowernode.crm.workbench.entity.ActivityRemark;
import com.bjpowernode.crm.workbench.entity.Clue;
import com.bjpowernode.crm.workbench.entity.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("workbench/clue")
public class ClueController {

    @Resource
    private UserService userService;

    @Resource
    private ClueService clueService;

    @Resource
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入到填充用户操作");


        List<User> users = userService.selectAllUser();

        return users;

    }


    @RequestMapping("/save.do")
    @ResponseBody
    public boolean save(HttpServletRequest request , Clue clue){
        System.out.println("进入到线索控制器增加操作");

        String id = UUIDUtil.getUUID();
        String createTime =DateTimeUtil.getSysTime();
        String createBy =((User)request.getSession().getAttribute("user")).getName();

        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);

        Boolean flag = clueService.save(clue);

        return flag;
    }

    @RequestMapping("/pageList.do")
    public void cluePageList(HttpServletRequest request ,HttpServletResponse response){
        System.out.println("进入到线索打印页面控制器操作");


        int pageNo =Integer.valueOf(request.getParameter("pageNo"));
        int pageSize =Integer.valueOf(request.getParameter("pageSize"));

        String fullname =request.getParameter("fullname");
        String company =request.getParameter("company");
        String phone =request.getParameter("phone");
        String source  =request.getParameter("source");
        String owner  =request.getParameter("owner");
        String mphone  =request.getParameter("mphone");
        String state  =request.getParameter("state");


        //计算略过记录数   limit （1,2）

        int skipCount = (pageNo-1) * pageSize;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("owner",owner);
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("source",source);
        map.put("mphone",mphone);
        map.put("state",state);

        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);


        PaginationVO<Clue> vo = clueService.selectPageListByElenment(map);

        PrintJson.printJsonObj(response,vo);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public void showdetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索控制器操作");

        String id = request.getParameter("id");

        Clue clue = clueService.selectById(id);

        request.setAttribute("clueDetail",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);

    }

    @RequestMapping("/getActivityListByClueId.do")
    public void showActivityList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索与活动控制器操作");

        String id = request.getParameter("clueId");

        List<Activity> activity = activityService.getActivityAndClue(id);


        PrintJson.printJsonObj(response,activity);
    }

    @RequestMapping("/deleteActivityAndClueById.do")
    @ResponseBody
    public boolean deleteActivityAndClueById(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索与活动控制器操作");

        String id = request.getParameter("clueId");

        boolean flag = clueService.deleteActivityAndClueById(id);

        return flag;
    }

    @RequestMapping("/getClueActivity.do")
    @ResponseBody
    public List<Activity> getClueActivity(@RequestParam("clueId") String clueId, @RequestParam("aname") String aname) {
        System.out.println("进入到线索与活动控制器---获取关联页面下的填充线索操作");

        List<Activity> activity = activityService.getClueActivity(clueId,aname);

        return activity;
    }

    @RequestMapping("/bund.do")
    @ResponseBody
    public boolean bundClueAndActivity(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("进入到线索与活动控制器操作---绑定关联页面下的活动操作");

        String cid = request.getParameter("cid");
        String aids[] = request.getParameterValues("aid");

        boolean flag = clueService.bundActivityAndClue(cid,aids);

        return flag;
    }

    @RequestMapping("/getActivityByName.do")
    @ResponseBody
    public List<Activity> getActivityByName(@RequestParam("aname") String aname) {
        System.out.println("进入到线索与活动控制器---查询市场活动列表（根据名称模糊查）");

        List<Activity> activity = clueService.getClueActivity(aname);

        return activity;
    }

    @RequestMapping("/convert.do")
    @ResponseBody
    public void convert(HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("进入到线索与活动控制器---转换为客户");

        String clueId =  request.getParameter("clueId");

        String flag =  request.getParameter("flag");

        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran t = null;

        if("a".equals(flag)){
            t = new Tran();

            String money =  request.getParameter("money");
            String name =  request.getParameter("name");
            String expectedDate =  request.getParameter("expectedDate");
            String stage =  request.getParameter("stage");
            String activityId =  request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();


            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);

        }

        boolean flag1 = clueService.convert(clueId,t,createBy);

        if (flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }

    }
}
