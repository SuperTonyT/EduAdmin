import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import com.google.gson.Gson;

@WebServlet(name = "A_AddActiveCourseServlet", urlPatterns = "/A_AddActiveCourseServlet")
public class A_AddActiveCourseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        Connection ct=sqlsrvdb.getConn();
        PreparedStatement pstmt=null;
        try{
            pstmt=ct.prepareStatement("insert into ActiveCourse values(?,?,?,?)");
            pstmt.setString(1, request.getParameter("term"));
            pstmt.setString(2, request.getParameter("c_number"));
            pstmt.setString(3, request.getParameter("t_number"));
            pstmt.setString(4, request.getParameter("time"));
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
