import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gson.Gson;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select 密码 from userTable where 用户名='"+request.getParameter("username")+"'";
        ResultSet rs=sqlsrvdb.executeQuery(sql);				//取得结果集
        try {
            if(rs.next())
            {
                if(rs.getString("密码").trim().compareTo(request.getParameter("oldp"))==0){
                    sql="update userTable set 密码='"+request.getParameter("newp")+"' where 用户名='"+request.getParameter("username")+"'";
                    sqlsrvdb.executeQuery(sql);
                    Gson gson = new Gson();
                    String result = gson.toJson(1);
                    response.getWriter().write(result);
                }
                else
                {
                    Gson gson = new Gson();
                    String result = gson.toJson(0);
                    response.getWriter().write(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
