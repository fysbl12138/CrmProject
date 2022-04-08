package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.entity.Customer;

import java.util.List;

/**
 * @author fangy
 * @date 2022-02-25 21:56
 */
public interface CustomerService {

    List<String> getCustomerName(String name);

}
