import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "A_GetTermsServlet", urlPatterns = "/A_GetTermsServlet")
public class A_GetTermsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select distinct 学期 from ActiveCourse";
        sqlsrvdb.executeQuery(sql);
        ArrayList al=new ArrayList();
        ResultSet rs=sqlsrvdb.executeQuery(sql);
        try {
            while(rs.next())
                al.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String result = gson.toJson(al);
        response.getWriter().write(result);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
