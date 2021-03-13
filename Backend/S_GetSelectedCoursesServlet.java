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

@WebServlet(name = "S_GetSelectedCoursesServlet", urlPatterns = "/S_GetSelectedCoursesServlet")
public class S_GetSelectedCoursesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select s.课号,c.课名,s.工号,t.姓名,上课时间 from SelectedCourse s,Course c,Teacher t,ActiveCourse a where s.课号=c.课号 and s.工号=t.工号 and a.学期=s.学期 and a.课号=s.课号 and a.工号=s.工号 and s.学期=(select * from courseState) and s.学号='"+request.getParameter("username")+"'";
        sqlsrvdb.executeQuery(sql);
        ArrayList al=new ArrayList();
        try {
            ResultSet rs=sqlsrvdb.executeQuery(sql);
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
