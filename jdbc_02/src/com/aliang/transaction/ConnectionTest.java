package com.aliang.transaction;

import com.aliang.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author Aliang
 * @create 2021-12-18 23:17
 */
public class ConnectionTest {
    @Test
    public  void testConnection() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
    }

}
