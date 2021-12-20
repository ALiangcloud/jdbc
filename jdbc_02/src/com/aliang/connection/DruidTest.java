package com.aliang.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author Aliang
 * @create 2021-12-19 22:45
 */
public class DruidTest {
    /**
     * 使用Druid创建数据库连接池获得连接
     * @throws Exception
     */
    @Test
    public void getConnection() throws Exception {

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        Properties pros =new Properties();
        pros.load(is);
        //创建Druid数据库连接池

        DataSource source = DruidDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);

    }

}
