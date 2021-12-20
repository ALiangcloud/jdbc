package com.aliang.preparedStatement.crud;

import com.aliang.bean.Customer;
import com.aliang.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Aliang
 * @create 2021-12-15 20:48
 */
public class CustomerForQuery {

    @Test
    public void testQueryForCustomers(){

        String sql = "select id,name,email,birth from customers where id = ?";
        Customer customer = queryForCustomers(sql, 13);
        System.out.println(customer);


    }

    public Customer queryForCustomers(String sql,Object ...args)  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获得连接
            conn = JDBCUtils.getConnection();
            //获得Statement
            ps = conn.prepareStatement(sql);

            //填充占位符
            for(int i=0;i< args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //执行并返回结果集合
            rs = ps.executeQuery();

            //处理结果集
            //获得结果集元数据，并获得结果集的列数
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            //获得一行中的每个值
            if(rs.next()){
                Customer cust = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i+1);
                    //通过结果集元数据获得结果集的列名
                    String columnName = rsmd.getColumnName(i + 1);
                    //通过反射，获得JavaBean的与结果集列名相同名称的属性
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    //给对应名称的属性赋值
                    field.set(cust,columnValue);
                }
                //返回获得的JavaBean对象
                return  cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return  null;
    }



    @Test
    public void testQuery1()  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id, name,email,birth  from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            //执行并返回结果集
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

    //            System.out.println("id = "+id+",name = " + name + ", email = " + email + ",birth =" + birth);
    //            Object[] data = new Object[]{id,name,email,birth};
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,resultSet);
        }



    }
}
