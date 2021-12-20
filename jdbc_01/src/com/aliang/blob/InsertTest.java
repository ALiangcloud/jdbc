package com.aliang.blob;

import com.aliang.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 批量插入数据
 * @author Aliang
 * @create 2021-12-18 22:00
 */
public class InsertTest {

    @Test
    public void testInsert3()  {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 2000000; i++) {
                ps.setString(1,"name_" +i);
                ps.addBatch();

                if (i % 500 ==0){
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }
            conn.commit();
            System.out.println("测试");
            long end = System.currentTimeMillis();
            System.out.println("所用时间：" + (end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }

    @Test
    public void testInsert2()  {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 2000000; i++) {
                ps.setString(1,"name_" +i);
                ps.addBatch();

                if (i % 500 ==0){
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }
            System.out.println("测试");
            long end = System.currentTimeMillis();
            System.out.println("所用时间：" + (end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }

    @Test
    public void testInsert1()  {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                ps.setString(1,"goods_" +i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("所用时间：" + (end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }
}
