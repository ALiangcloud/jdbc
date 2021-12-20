package com.aliang.dao;

/**
 * @author Aliang
 * @create 2021-12-19 19:21
 */

import com.aliang.bean.Customer;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 此接口用于规范针对于customers表的常用操作
 */

public interface CustomerDAO {

    /**
     * 将cust对象添加到数据库中
     * @param conn
     * @param cust
     */
    void insert(Connection conn, Customer cust);

    /**
     * 通过id删除数据
     * @param conn
     * @param id
     */
    void deleteById(Connection conn,int id);


    /**
     * 通过对象修改数据
     * @param conn
     * @param cust
     */
    void updateById(Connection conn,Customer cust);

    /**
     * 通过id查询数据
     * @param conn
     * @param id
     */
    Customer getCustomerById(Connection conn ,int id);


    /**
     * 查询所有数据
     * @param conn
     * @return
     */
    List<Customer> getAll(Connection conn);


    /**
     * 查询数量
     * @param conn
     * @return
     */
    Long getCount(Connection conn);

    /**
     * 获得最大岁数
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
