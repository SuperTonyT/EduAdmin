import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SetCourseStateServlet", urlPatterns = "/SetCourseStateServlet")
public class SetCourseStateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String term=request.getParameter("term");
        String sql="insert into courseState values ('"+term+"')";
        sqlsrvdb.executeQuery(sql);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
