package com.aliang.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Aliang
 * @create 2021-12-19 22:16
 */
public class DBCPTest {

    /**
     * 方式二使用配置文件
     */
    @Test
    public void testGetConnection2() throws Exception {

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        Properties pros = new Properties();
        pros.load(is);

        DataSource source = BasicDataSourceFactory.createDataSource(pros);
        Connection conn = source.getConnection();
        System.out.println(conn);

    }




    /**
     * 方式一：测试dbcp数据库连接池
     */
    @Test
    public void testGetConnection() throws SQLException {
        //创建dbcp数据库连接池
        BasicDataSource source = new BasicDataSource();

        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test");
        source.setUsername("root");
        source.setPassword("123456");

        //设置数据库连接池管理属性
        source.setInitialSize(10);
        source.setMaxActive(10);

        //获得连接
        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
