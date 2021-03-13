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

@WebServlet(name = "S_GetScoresServlet", urlPatterns = "/S_GetScoresServlet")
public class S_GetScoresServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        String sql="select s.学期,s.课号,c.课名,s.工号,t.姓名,上课时间,s.平时成绩,s.考试成绩,s.总评成绩 from SelectedCourse s,Course c,Teacher t,ActiveCourse a where s.课号=c.课号 and s.工号=t.工号 and a.学期=s.学期 and a.课号=s.课号 and a.工号=s.工号 and s.学号='"+request.getParameter("username")+"'";
        sqlsrvdb.executeQuery(sql);
        ArrayList al=new ArrayList();
        try {
            ResultSet rs=sqlsrvdb.executeQuery(sql);
            while(rs.next())
            {
                S_ScoreTable score=new S_ScoreTable();
                score.setTerm(rs.getString(1));
                score.setC_number(rs.getString(2));
                score.setC_name(rs.getString(3));
                score.setT_number(rs.getString(4));
                score.setT_name(rs.getString(5));
                score.setTime(rs.getString(6));
                score.setAssignment_score(rs.getFloat(7));
                score.setTest_score(rs.getFloat(8));
                score.setScore(rs.getFloat(9));
                al.add(score);
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
