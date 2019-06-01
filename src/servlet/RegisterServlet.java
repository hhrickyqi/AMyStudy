package servlet;

import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import pool.C3P0Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        //解决从前端获取的乱码问题
        req.setCharacterEncoding("UTF-8");
        /*
        //传统方法,比较复杂
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        register(user);*/
        try {
            String sex = "";
            User user = new User();
            Map<String, String[]> parameterMap = req.getParameterMap();
            BeanUtils.populate(user , parameterMap);
            Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            for (Map.Entry<String, String[]> e : entries){
                if (e.getKey().equals("sex")){
                    for (String s : e.getValue()){
                        sex += s;
                    }
                }
            }
            System.out.println("sex");
            user.setSex(sex);
            register(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
    public void register(User user){

        try {
            //创建QueryRunner对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            System.out.println("qr = " + qr);
            //添加用户
            String sql = "insert into t_user values (null ,? , ? , ? , ? , ?)";
            qr.update(sql , user.getName(),user.getPassword(),user.getEmail(),user.getName(),user.getSex());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
