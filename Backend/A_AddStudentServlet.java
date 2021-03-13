import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import com.google.gson.Gson;

@WebServlet(name = "A_AddStudentServlet", urlPatterns = "/A_AddStudentServlet")
public class A_AddStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        Connection ct=sqlsrvdb.getConn();
        PreparedStatement pstmt=null;
        try{
            pstmt=ct.prepareStatement("insert into Student values(?,?,?,?,?,?,?)");
            pstmt.setString(1, request.getParameter("number"));
            pstmt.setString(2, request.getParameter("name"));
            pstmt.setString(3, request.getParameter("sex"));
            pstmt.setString(4, request.getParameter("birthday"));
            pstmt.setString(5, request.getParameter("birthplace"));
            pstmt.setString(6, request.getParameter("t_num"));
            pstmt.setString(7, request.getParameter("institute"));
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
