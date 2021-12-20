package com.aliang.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Aliang
 * @create 2021-12-19 22:07
 */
public class JDBCUtils2 {

    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
    public static Connection getConnection() throws SQLException {
        Connection conn = cpds.getConnection();
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
