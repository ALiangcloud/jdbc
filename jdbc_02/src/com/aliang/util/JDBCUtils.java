package com.aliang.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Aliang
 * @create 2021-12-15 19:49
 */
public class JDBCUtils {


    public static Connection getConnection() throws Exception {
        //读取配置信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        //注册驱动
        Class.forName(driverClass);

        //获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public  static  void closeResource(Connection conn, Statement ps){

        try {
            if (conn != null ) conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (ps != null) ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public  static  void closeResource(Connection conn, Statement ps, ResultSet rs){

        try {
            if (conn != null ) conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (ps != null) ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (rs != null ) rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
