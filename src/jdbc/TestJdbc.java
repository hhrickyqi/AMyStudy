package jdbc;

import domain.User;
import jdbc.utils.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;
import pool.C3P0Utils;
import pool.DBCPUtils;
import pool.MyConnectionPool;

import java.sql.*;
import java.util.List;


public class TestJdbc {

    @Test
    public void testlogin() {
        login("吴亦凡", "fanfan");
    }

    //查询

    @Test
    public void testQueryRunner(){
        try {
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            String sql = "select count(*) from t_user";
            Object o = qr.query(sql , new ScalarHandler());
            System.out.println(o);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //删除

    @Test
    public void deleteUser(){
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBCPUtils.getConnection();
            String sql = "delete from t_user where id = 1";
            pst = conn.prepareStatement(sql);
            int rows = pst.executeUpdate();
            if (rows > 0){
                System.out.println("删除成功！");
            }else {
                System.out.println("删除失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新用户


    @Test
    public void updateUser() {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            String sql = "update t_user set username = '测试C3P0' where id = 1";
            conn = C3P0Utils.getConnection();
            pst = conn.prepareStatement(sql);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("修改成功！");
            } else {
                System.out.println("修改失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertUser(){
        Connection conn = null;
        PreparedStatement pst = null;
        MyConnectionPool connectionPool = null;
        try {
            String sql = "insert into t_user values('1' , '吴亦凡' , 'fanfan' , 'kriswu@qq.com')";
            connectionPool = new MyConnectionPool();
            conn = connectionPool.getConnection();
            pst = conn.prepareStatement(sql);
            int rows = pst.executeUpdate();
            if(rows > 0){
                System.out.println("添加成功！");
            }else{
                System.out.println("添加失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConn(conn);
        }

    }




    public void login(String username,String password){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from t_user where username = ? and password = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,password);
            rs = pst.executeQuery();
            if (rs.next()){
                System.out.println(sql);
                System.out.println("欢迎" + username);
            }else {
                System.out.println("用户名或密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.releaseRes(conn,pst,rs);
        }

    }

    @Test
    public void testQuery() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            /*//加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306" +
                    "/web01", "root", "mysqlhpq120112");
            //获取stateMent对象
            st = conn.createStatement();
            //编写sql语句
            String sql = "select * from t_user";
            //执行sql语句
            rs = st.executeQuery(sql);//查询*/
            conn = JDBCUtils.getConnection();
            st = conn.createStatement();
            String sql = "select * from t_user";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Id:" + rs.getInt(1) + " 用户名：" +
                        rs.getString("username") + " 邮箱：" +
                        rs.getString(4) + " 密码：" +
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.releaseRes(conn, st, rs);
        }
    }
}
