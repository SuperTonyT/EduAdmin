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

@WebServlet(name = "S_GetCoursesServlet", urlPatterns = "/S_GetCoursesServlet")
public class S_GetCoursesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select a.课号,c.课名,a.工号,t.姓名,上课时间 from ActiveCourse a,Course c,Teacher t where a.课号=c.课号 and a.工号=t.工号 and a.学期=(select * from courseState)";
        sqlsrvdb.executeQuery(sql);
        ArrayList al=new ArrayList();
        ResultSet rs=sqlsrvdb.executeQuery(sql);
        try {
            while(rs.next())
            {
                S_CourseTable course=new S_CourseTable();
                course.setC_number(rs.getString(1));
                course.setC_name(rs.getString(2));
                course.setT_number(rs.getString(3));
                course.setT_name(rs.getString(4));
                course.setTime(rs.getString(5));
                al.add(course);
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
