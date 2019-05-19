package jdbc.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCUtils {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    static {
        /*ResourceBundle专门用来处理properties
        文件，提供getBundle()方法只需填写文件即可*/
        ResourceBundle rb = ResourceBundle.getBundle("db");
        driver = rb.getString("jdbc.driver");
        url = rb.getString("jdbc.url");
        username = rb.getString("jdbc.username");
        password = rb.getString("jdbc.password");
    }
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(driver);//加载驱动
            conn = DriverManager.getConnection(url,username,password);//获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    /*释放资源*/
    public static void releaseRes(Connection conn , Statement st , ResultSet rs){
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != st) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
