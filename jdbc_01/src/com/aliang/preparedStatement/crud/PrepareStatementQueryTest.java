package com.aliang.preparedStatement.crud;

import com.aliang.bean.Customer;
import com.aliang.bean.Order;
import com.aliang.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliang
 * @create 2021-12-16 20:27
 */
public class PrepareStatementQueryTest {

    @Test
    public  void testGetList(){
        String sql = "select id,name,email from customers";
        List<Customer> list = getList(Customer.class, sql);
        list.forEach(System.out::println);

    }

    public <T> List<T> getList(Class<T> clazz,String sql,Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获得连接
            conn = JDBCUtils.getConnection();
            //通过连接获得Statement
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行并获得结果集
            rs = ps.executeQuery();
            //处理结果集
            //获得结果集的元数据并获得结果集的列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object resultValue = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,resultValue);
                }
                list.add(t);
            }
            return  list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void testGetInstance(){
        String sql = "select order_id orderId,order_name as orderName from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql, 1);
        System.out.println(order);

        sql = "select id ,name  from customers where id = ?";
        Customer customer = getInstance(Customer.class, sql, 1);
        System.out.println(customer);

    }


    public <T> T getInstance(Class<T> clazz,String sql,Object ...args)  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获得连接
            conn = JDBCUtils.getConnection();
            //通过连接获得Statement
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行并获得结果集
            rs = ps.executeQuery();
            //处理结果集
            //获得结果集的元数据并获得结果集的列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object resultValue = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,resultValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;

    }
}
