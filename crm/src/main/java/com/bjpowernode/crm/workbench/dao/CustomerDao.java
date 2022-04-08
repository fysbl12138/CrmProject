package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.entity.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String companyName);

    int save(Customer cus);

    List<String> getCustomerName(String name);
}
