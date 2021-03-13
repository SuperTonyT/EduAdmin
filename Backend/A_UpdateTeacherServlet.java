import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import com.google.gson.Gson;

@WebServlet(name = "A_UpdateTeacherServlet", urlPatterns = "/A_UpdateTeacherServlet")
public class A_UpdateTeacherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        Connection ct=sqlsrvdb.getConn();
        PreparedStatement pstmt=null;
        try{
            pstmt=ct.prepareStatement("update Teacher set 姓名=?,性别=?,出生日期=?,职称=?,基本工资=?,院系号=? where 工号=?");
            pstmt.setString(1, request.getParameter("name"));
            pstmt.setString(2, request.getParameter("sex"));
            pstmt.setString(3, request.getParameter("birthday"));
            pstmt.setString(4, request.getParameter("title"));
            pstmt.setFloat(5, Float.parseFloat(request.getParameter("salary")));
            pstmt.setString(6, request.getParameter("institute"));
            pstmt.setString(7, request.getParameter("number"));
            pstmt.executeUpdate();
            Gson gson = new Gson();
            String result = gson.toJson(1);
            response.getWriter().write(result);
        }catch(SQLException e){
            e.printStackTrace();
            Gson gson = new Gson();
            String result = gson.toJson(e);
            response.getWriter().write(result);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
