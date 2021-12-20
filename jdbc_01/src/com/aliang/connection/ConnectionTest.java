package com.aliang.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void testConnection1() throws Exception{
        //获得驱动
        Driver driver = new com.mysql.jdbc.Driver();
        //通过驱动获得连接
        //url：传入数据库链接
        //info：至少传入数据库用户和密码
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");
        //获得连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    @Test
    public void testConnection2() throws Exception {
        //1获取驱动，反射实现
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        //2通过驱动连接数据库
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");

        Connection conn = driver.connect(url, info);
        //3打印连接
        System.out.println(conn);
    }

    @Test
    public void testConnextion3() throws Exception {
        //获得驱动
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //提供信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";

        //驱动管理注册驱动
        DriverManager.registerDriver(driver);

        //通过驱动管理获得连接
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

    @Test
    public void testConnextion4() throws Exception {

        //提供信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";

        //注册驱动：mysql的驱动中，实现了驱动的注册
        Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) clazz.newInstance();
//        //驱动管理注册驱动
//        DriverManager.registerDriver(driver);

        //通过驱动管理获得连接
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }


    //通过配置文件读取数据库信息
    @Test
    public void testConnextion5()  throws  Exception{
        //读取配置信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

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
        System.out.println(conn);

    }

}
