import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import table.*;

@WebServlet(name = "A_GetStudentsServlet", urlPatterns = "/A_GetStudentsServlet")
public class A_GetStudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select * from Student";
        sqlsrvdb.executeQuery(sql);
        ArrayList al=new ArrayList();
        ResultSet rs=sqlsrvdb.executeQuery(sql);
        try {
            while(rs.next())
            {
                A_StudentTable student=new A_StudentTable();
                student.setNumber(rs.getString(1));
                student.setName(rs.getString(2));
                student.setSex(rs.getString(3));
                student.setBirthday(rs.getString(4));
                student.setBirthplace(rs.getString(5));
                student.setT_num(rs.getString(6));
                student.setInstitute(rs.getString(7));
                al.add(student);
            }
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
