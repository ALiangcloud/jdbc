package com.aliang.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Aliang
 * @create 2021-12-19 22:07
 */
public class JDBCUtils4 {

    private static DataSource source;
    static {
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            Properties pros = new Properties();
            pros.load(is);
            //创建
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws Exception {

        Connection conn = source.getConnection();
        return  conn;
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
