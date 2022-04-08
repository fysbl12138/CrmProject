package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.VO.PaginationVO;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.entity.Customer;
import com.bjpowernode.crm.workbench.entity.Tran;
import com.bjpowernode.crm.workbench.entity.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-25 20:42
 */
@Service
public class TranServiceImpl implements TranService {

    @Resource
    private CustomerDao customerDao;

    @Resource
    private TranDao tranDao;

    @Resource
    private TranHistoryDao tranHistoryDao;

    @Override
    public boolean save(Tran tran, String accountName) {

        boolean falg = true;

        Customer customer = customerDao.getCustomerByName(accountName);
        if(customer == null){

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(accountName);
            customer.setOwner(tran.getOwner());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setDescription(tran.getDescription());

            int count = customerDao.save(customer);
            if(count != 1){

                falg = false;

            }

        }

        tran.setCustomerId(customer.getId());

        int count2 = tranDao.save(tran);
        if(count2 != 1){

            falg = false;

        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setTranId(tran.getId());
        int count3 = tranHistoryDao.save(tranHistory);
        if(count3 != 1){
            falg = false;
        }

        return falg;
    }


    @Override
    public PaginationVO<Tran> selectPageListByElenment(Map<String, Object> map) {
        int total = tranDao.getTotal();

        List<Tran> dataList = tranDao.selectPageList(map);

        PaginationVO<Tran> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public Tran getDetailById(String id) {

        Tran tran = tranDao.getDetailById(id);

        return tran;
    }

    @Override
    public List<TranHistory> getHistoryListById(String id) {

        List<TranHistory> tranHistory = tranHistoryDao.getHistoryListById(id);

        return tranHistory;
    }

    @Override
    public boolean changeStage(Tran tran) {

        boolean flag = true;
        int count = tranDao.changeStage(tran);
        if(count != 1)
        {
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(tran.getEditBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setTranId(tran.getId());

        int count2 = tranHistoryDao.save(tranHistory);
        if(count2 != 1)
        {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getEchartsPageList() {

        int total = tranDao.getEchartsTotal();

        List<Map<String ,Object>> maps  = tranDao.getEchartsPageList();

        Map<String ,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",maps);

        return map;
    }
}
