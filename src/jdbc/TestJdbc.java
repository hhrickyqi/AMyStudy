package jdbc;

import jdbc.utils.JDBCUtils;

import org.junit.Test;

import java.sql.*;

public class TestJdbc {
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
            st =  conn.createStatement();
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
            JDBCUtils.releaseRes(conn , st , rs);
        }
    }
}
