package com.aliang.exer;

import com.aliang.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * @author Aliang
 * @create 2021-12-16 22:20
 */
public class Exer1Test {

    public static void main(String[] args) {
        testInsert();
    }

    public static void testInsert(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入名称:");
        String name = scanner.next();
        System.out.print("请输入邮箱:");
        String email = scanner.next();
        System.out.print("请输入出生日期:");
        String birth = scanner.next();
        String sql = "insert into customers(name,email,birth)value(?,?,?)";
        int update = update(sql, name, email, birth);

        if (update>0){
            System.out.println("更新成功");
        }else{
            System.out.println("更新失败");
        }


    }
    public static int update(String sql, Object... args)  {

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
