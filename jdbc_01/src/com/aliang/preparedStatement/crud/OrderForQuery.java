package com.aliang.preparedStatement.crud;

import com.aliang.bean.Order;
import com.aliang.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Aliang
 * @create 2021-12-15 21:48
 */
public class OrderForQuery {
    /**
     * 针对表的字段名和JavaBean类的属性名不一致时，必须在sql声明时对字段起别名
     * 并在结果集元数据使用getColumnLabel获得结果集的列名（别名）
     *
     */
    @Test
    public void testOrderForQuery(){
        String sql ="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = orderForQuery(sql, 1);
        System.out.println(order);
    }

    public  Order orderForQuery(String sql ,Object...args)  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获得连接
            conn = JDBCUtils.getConnection();
            //获得statement
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行并获得结果集
            rs = ps.executeQuery();
            //获得结果集元数据并通过元数据获得结果集列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            //处理结果集
            if (rs.next()) {
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //获得列值
                    Object columnValue = rs.getObject(i + 1);
                    //获得列名
//                    String columnName = metaData.getColumnName(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //通过反射将指定列名的属性赋值为指定的值
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order,columnValue);

                }
                 return  order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return  null;
    }


    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获得连接
            conn = JDBCUtils.getConnection();
            //获得statement
            String sql = "select order_id, order_name,order_date from `order` where order_id = ?";
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setObject(1,1);

            //执行并返回结果集
            rs = ps.executeQuery();

            //处理结果集
            if(rs.next()){
                int orderId = rs.getInt(1);
                String orderName = rs.getString(2);
                Date orderDate = rs.getDate(3);
                //生成JavaBean对象
                Order order = new Order(orderId, orderName, orderDate);
                System.out.println(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }


    }


}
