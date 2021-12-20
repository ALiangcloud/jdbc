package com.aliang.dao;

import com.aliang.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装了对数据表的通用操作
 * @author Aliang
 * @create 2021-12-19 19:01
 */
public abstract class BaseDAO {
    //通用增删改操作，version2.0，考虑事务
    public int update(Connection conn, String sql, Object... args)  {
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


    //通用查询，用于返回一条数据 version2.0：考虑事务
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
    //通用查询，用于返回多条数据 version2.0：考虑事务
    public <T> List<T> getForList(Connection conn,Class<T> clazz, String sql, Object ...args){
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
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object resultValue = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,resultValue);
                }
                list.add(t);
            }
            return  list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;
    }
    //特殊查询
    public <E>E getValue(Connection conn,String sql,Object ...args)  {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;
    }

}
