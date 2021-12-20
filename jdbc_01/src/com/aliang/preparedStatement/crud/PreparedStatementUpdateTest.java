package com.aliang.preparedStatement.crud;

import com.aliang.util.JDBCUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author Aliang
 * @create 2021-12-15 19:14
 */
public class PreparedStatementUpdateTest {

    @Test
    public void testCommonUpdate(){
//        String sql = "delete from customers where id = ?";
//        update(sql,3);

        String sql = "update `order` set order_name = ? where order_id = ?";
        update(sql , "MM",4);
    }




    public void update(String sql,Object ...args)  {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //获得连接
            conn = JDBCUtils.getConnection();
            //获得prepareedstatement
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0;i <args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn,ps);
        }

    }

    @Test
    public void updateTest()  {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //获取数据库的连接
            conn = JDBCUtils.getConnection();
            //预编译sql语句
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setObject(1,"莫扎特");
            ps.setObject(2,19);

            //执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //资源的关闭
            JDBCUtils.closeResource(conn,ps);
        }


    }


    /**
     * 实现插入操作
     */
    @Test
    public void InsertTest()  {
        //获取连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String driverClass = pros.getProperty("driverClass");
            String url = pros.getProperty("url");
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
            //预编译sql语句
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setString(1,"哪吒");
            ps.setString(2,"nezha@gmail.com");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            ps.setDate(3,new Date(date.getTime()));

            //执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (conn !=null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }





    }

}
