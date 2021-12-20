package com.aliang.blob;

import com.aliang.util.JDBCUtils;
import com.aliang.bean.*;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author Aliang
 * @create 2021-12-18 20:39
 */
public class TestBlob {

    @Test
    public void testQuery()  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();

            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);

            ps.setObject(1,23);

            rs = ps.executeQuery();

            if (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String eamil = rs.getString(3);
                Date birth = rs.getDate(4);

                Customer cust = new Customer(id, name, eamil, birth);
                System.out.println(cust);
                Blob photo = rs.getBlob(5);

                is = photo.getBinaryStream();

                fos = new FileOutputStream("紫霞1.jpg");
                byte[] buffer = new byte[1024];

                int len;
                while ((len=is.read(buffer) )!= -1){
                    fos.write(buffer,0,len);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn,ps,rs);
        }
    }

    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream fis = null;
        try {
            conn = JDBCUtils.getConnection();

            String sql = "update customers set photo = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(2, 22);
            fis = new FileInputStream(new File("紫霞.jpeg"));
            ps.setBlob(1, fis);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JDBCUtils.closeResource(conn, ps);
        }
    }



    @Test
    public void testDelete()  {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream fis = null;
        try {
            conn = JDBCUtils.getConnection();

            String sql = "delete from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,24);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }

    }






    @Test
    public void testInsert()  {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream fis = null;
        try {
            conn = JDBCUtils.getConnection();

            String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"紫霞");
            ps.setObject(2,"zixia@qq.com");
            ps.setObject(3,"2021-11-11");
            fis = new FileInputStream(new File("紫霞.jpeg"));
            ps.setBlob(4,fis);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JDBCUtils.closeResource(conn,ps);
        }

    }
}
