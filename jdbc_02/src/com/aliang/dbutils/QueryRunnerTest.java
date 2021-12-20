package com.aliang.dbutils;

import com.aliang.bean.*;
import com.aliang.util.JDBCUtils4;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @author Aliang
 * @create 2021-12-19 23:09
 *
 * commons-dbutils 是Apache组织提供的JDBC工具类库，封装了对数据库的增删改查操作
 * 一条数据使用BeanHeader，MapHeader
 * 多条记录使用BeanListHeader，MapListHeader
 * 特殊值使用ScalarHeader
 *
 */
public class QueryRunnerTest {

    //测试查询
    @Test
    public void testQuery2()  {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils4.getConnection();
            String sql = "select name,email,birth from customers where id < ?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            List<Customer> customers = runner.query(conn, sql, handler, 23);
            System.out.println(customers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils4.closeResource(conn,null);
        }

    }
    //测试查询
    @Test
    public void testQuery()  {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils4.getConnection();
            String sql = "select name,email,birth from customers where id = ?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = runner.query(conn, sql, handler, 23);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils4.closeResource(conn,null);
        }

    }
    //测试插入
    @Test
    public void testInsert()  {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils4.getConnection();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int update = runner.update(conn, sql, "蔡徐坤", "caixukui@126.com", "1997-09-08");
            System.out.println("添加了"+update +"记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils4.closeResource(conn,null);
        }

    }


}
