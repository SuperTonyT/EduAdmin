import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gson.Gson;

@WebServlet(name = "GetCourseStateServlet", urlPatterns = "/GetCourseStateServlet")
public class GetCourseStateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select * from courseState";
        ResultSet rs=sqlsrvdb.executeQuery(sql);
        try {
            Gson gson = new Gson();
            if (rs.next())
            {
                String result = gson.toJson(rs.getString(1));
                response.getWriter().write(result);
            }
            else
            {
                String result = gson.toJson(0);
                response.getWriter().write(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
