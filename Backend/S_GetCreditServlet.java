import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import com.google.gson.Gson;

@WebServlet(name = "S_GetCreditServlet", urlPatterns = "/S_GetCreditServlet")
public class S_GetCreditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        Connection ct=sqlsrvdb.getConn();
        try{
            CallableStatement cstmt=ct.prepareCall("{call GetCredit(?,?,?)}");
            cstmt.setString(1, request.getParameter("s_number"));
            cstmt.setString(2, request.getParameter("term"));
            cstmt.registerOutParameter(3,Types.FLOAT);
            cstmt.execute();
            float credit=cstmt.getFloat(3);
            Gson gson = new Gson();
            String result = gson.toJson(credit);
            response.getWriter().write(result);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
