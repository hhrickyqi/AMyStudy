package jdbc;

import jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSql {
    @Test
    public void distinct(){
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select DISTINCT cname from t_category ";
            pst = conn.prepareStatement(sql);
            int rows = pst.executeUpdate();
            if (rows > 0){
                System.out.println("去重成功");
            }else {
                System.out.println("去重失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
