import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import com.google.gson.Gson;


@WebServlet(name = "A_UpdateCourseServlet", urlPatterns = "/A_UpdateCourseServlet")
public class A_UpdateCourseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        Connection ct=sqlsrvdb.getConn();
        PreparedStatement pstmt=null;
        try{
            pstmt=ct.prepareStatement("update Course set 课名=?,学分=?,考试成绩占比=?,院系号=? where 课号=?");
            pstmt.setString(1, request.getParameter("name"));
            pstmt.setFloat(2, Float.parseFloat(request.getParameter("credit")));
            pstmt.setString(3, request.getParameter("ratio"));
            pstmt.setString(4, request.getParameter("institute"));
            pstmt.setString(5, request.getParameter("number"));
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
