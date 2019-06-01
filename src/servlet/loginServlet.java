package servlet;

import domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import pool.C3P0Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class loginServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{


        //解决中文问题!!!!!!!!!!!!!!!!!!!!
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username + "," + password);
        login(username , password , resp);
    }
    public void login(String username, String password, HttpServletResponse resp){
        try {

            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            String sql = "select * from t_user where username = ? and password = ?";
            User user = qr.query(sql,new BeanHandler<>(User.class),username,password);
            System.out.println(user);
            resp.setContentType("text/html;charset=UTF-8");
            if (user != null){
                resp.getWriter().write("登陆成功!");
            }else {
                resp.getWriter().write("登陆失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
