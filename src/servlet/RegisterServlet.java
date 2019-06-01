package servlet;

import org.apache.commons.dbutils.QueryRunner;
import pool.C3P0Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
    }
    public void register(String username , String password , String repassword , String email , String name , HttpServletResponse resp){

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    }
}
