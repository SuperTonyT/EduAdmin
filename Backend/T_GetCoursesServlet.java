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

@WebServlet(name = "T_GetCoursesServlet", urlPatterns = "/T_GetCoursesServlet")
public class T_GetCoursesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select x.学期,c.课名,a.上课时间,y.学号,y.姓名,x.平时成绩,x.考试成绩,x.总评成绩,x.课号 from SelectedCourse x,Student y,ActiveCourse a,Course c where x.学号=y.学号 and x.学期=a.学期 and x.课号=a.课号 and x.工号=a.工号 and x.课号=c.课号 and x.工号='"+request.getParameter("t_number")+"'";
        sqlsrvdb.executeQuery(sql);
        ArrayList al=new ArrayList();
        ResultSet rs=sqlsrvdb.executeQuery(sql);
        try {
            while(rs.next())
            {
                T_CourseTable course=new T_CourseTable();
                course.setTerm(rs.getString(1));
                course.setC_name(rs.getString(2));
                course.setTime(rs.getString(3));
                course.setS_number(rs.getString(4));
                course.setS_name(rs.getString(5));
                course.setAssignment_score(rs.getFloat(6));
                course.setTest_score(rs.getFloat(7));
                course.setScore(rs.getFloat(8));
                course.setC_number(rs.getString(9));
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
