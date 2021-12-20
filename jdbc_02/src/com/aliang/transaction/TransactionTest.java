package com.aliang.transaction;

import com.aliang.bean.User;
import com.aliang.util.JDBCUtils;
import com.aliang.util.JDBCUtils4;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author Aliang
 * @create 2021-12-18 23:49
 */
public class TransactionTest {

    @Test
    public void testTransactionSelect() throws Exception {

        Connection conn = JDBCUtils4.getConnection();
        //获得当前的隔离级别
        System.out.println(conn.getTransactionIsolation());
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //设置不自动提交
        conn.setAutoCommit(false);
        //查询
        String sql = "select user,password,balance from user_table where user = ?";
        User user = getInstance(conn, User.class, sql, "CC");
        System.out.println(user);
    }

    @Test
    public void testTransactionUpdate() throws Exception {
        Connection conn = JDBCUtils.getConnection();

        //设置不自动提交
        conn.setAutoCommit(false);
        String sql = "update user_table set balance = 5000 where user = ?";
        update(conn,sql,"CC");

        Thread.sleep(15000);

        System.out.println("修改结束");

    }
    //通用查询，version2.0：考虑事务
    public <T> T getInstance(Connection conn, Class<T> clazz,String sql,Object ...args)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //通过连接获得Statement
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行并获得结果集
            rs = ps.executeQuery();
            //处理结果集
            //获得结果集的元数据并获得结果集的列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object resultValue = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,resultValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;

    }

    @Test
    public void testUpdateWithTx() throws Exception {

        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            System.out.println(conn.getAutoCommit());
            conn.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            update(conn,sql1,"AA");

            //模拟异常
            System.out.println(10/0);

            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            update(conn,sql2,"BB");

            System.out.println("转账成功");
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            JDBCUtils.closeResource(conn,null);
        }


    }
    //通用增删改操作，version2.0，考虑事务
    public int update(Connection conn,String sql, Object... args)  {
        PreparedStatement ps = null;
        try {
            //获得prepareedstatement
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0;i <args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(null,ps);
        }
        return 0;
    }

    @Test
    public void testUpdate(){
        String sql1 = "update user_table set balance = balance - 100 where user = ?";
        update(sql1,"AA");

        //模拟异常
        System.out.println(10/0);

        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        update(sql2,"BB");

        System.out.println("转账成功");

    }


    public int update(String sql, Object... args)  {
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
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn,ps);
        }
        return 0;
    }
}
