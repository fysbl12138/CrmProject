package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.entity.Tran;
import com.bjpowernode.crm.workbench.entity.TranHistory;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * @date 2022-02-25 20:45
 */
@Controller
@RequestMapping("workbench/transaction")
public class TranController {

    @Resource
    private UserService userService;

    @Resource
    private TranService tranService;

    @Resource
    private CustomerService customerService;

    @RequestMapping("/add.do")
    @ResponseBody
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到交易添加页的操作");


        List<User> users = userService.selectAllUser();

        request.setAttribute("uList",users);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);

    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入到自动补全客户名称的操作");

        String name = request.getParameter("name");

        List<String> names = customerService.getCustomerName(name);

        return names;
    }

    @RequestMapping(value = "/addtran.do",method = RequestMethod.POST)
    public void AddTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到创建交易的操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String accountName = request.getParameter("accountName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String contactsId = request.getParameter("contactsId");
        String activityId = request.getParameter("activityId");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");


        Tran tran = new Tran();
        tran.setContactSummary(contactSummary);
        tran.setType(type);
        tran.setNextContactTime(nextContactTime);
        tran.setOwner(owner);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setSource(source);
        tran.setStage(stage);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setMoney(money);
        tran.setId(id);
        tran.setContactsId(contactsId);
        tran.setDescription(description);
        tran.setActivityId(activityId);


        boolean flag = tranService.save(tran,accountName);

        if(flag){

            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");

        }

    }
    @RequestMapping("/getTranList.do")
    @ResponseBody
    public void getTranList(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入到自动补全客户名称的操作");

        int pageNo =Integer.valueOf(request.getParameter("pageNo"));
        int pageSize =Integer.valueOf(request.getParameter("pageSize"));

        String name =request.getParameter("name");
        String customer =request.getParameter("customer");
        String stage =request.getParameter("stage");
        String contact =request.getParameter("contact");
        String source  =request.getParameter("source");
        String owner  =request.getParameter("owner");
        String type  =request.getParameter("type");


        int skipCount = (pageNo-1) * pageSize;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("customer",customer);
        map.put("contact",contact);
        map.put("source",source);
        map.put("type",type);
        map.put("stage",stage);

        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);


        PaginationVO<Tran> vo = tranService.selectPageListByElenment(map);

        PrintJson.printJsonObj(response,vo);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到交易添加页的操作");

        String id = request.getParameter("id");

        Tran tran = tranService.getDetailById(id);

        String stage = tran.getStage();

        Map<String,String > map = (Map<String, String>) request.getServletContext().getAttribute("kmap");
        String possibility = map.get(stage);

        tran.setPossibility(possibility);

        request.setAttribute("tran",tran);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);

    }

    @RequestMapping("/getHistoryList.do")
    @ResponseBody
    public List<TranHistory> getHistoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到交易添加页的操作");

        String id = request.getParameter("id");

        List<TranHistory> tranHistorys = tranService.getHistoryListById(id);
//
//        Map<String, List<TranHistory>> map = new HashMap<>();
//        map.put("data",tranHistory);

        Map<String,String > map = (Map<String, String>) request.getServletContext().getAttribute("kmap");

        for (TranHistory tranHistory : tranHistorys) {
            String stage = tranHistory.getStage();
            String possibility = map.get(stage);

            tranHistory.setPossibility(possibility);
        }

        return tranHistorys;
    }

    @RequestMapping("/changeStage.do")
    public void changeStage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("执行改变阶段的操作");

        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        Map<String,String > map1 = (Map<String, String>) request.getServletContext().getAttribute("kmap");
        String possibility = map1.get(stage);


        Tran tran = new Tran();
        tran.setId(id);
        tran.setStage(stage);
        tran.setMoney(money);
        tran.setExpectedDate(expectedDate);
        tran.setEditBy(editBy);
        tran.setEditTime(editTime);
        tran.setPossibility(possibility);

        boolean flag = tranService.changeStage(tran);

        Map<String ,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("t",tran);

        PrintJson.printJsonObj(response,map);
    }

    @RequestMapping("/pageList.do")
    public void pageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("为echarts图提供数据");

        Map<String ,Object> map = tranService.getEchartsPageList();


        PrintJson.printJsonObj(response,map);
    }


}
