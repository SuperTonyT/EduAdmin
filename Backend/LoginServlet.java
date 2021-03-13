import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import table.*;
import com.google.gson.Gson;

@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*"); //允许所有域名访问
        request.setCharacterEncoding("UTF-8");				//设置请求编码
        response.setCharacterEncoding("UTF-8");
        String usr=request.getParameter("username");			//获取提交的用户名
        String pwd=request.getParameter("password");			//获取提交的密码
        boolean validated=false;							//验证成功标识
        SqlSrvDBConn sqlsrvdb=new SqlSrvDBConn();
        HttpSession session=request.getSession();		//获得会话对象，用来保存当前登录用户的信息
        UserTable user=null;
        //先获得UserTable对象，如果是第一次访问该页，用户对象肯定为空，但如果是第二次甚至是第三次，
        //就直接登录主页而无须再次重复验证该用户的信息
        user=(UserTable)session.getAttribute("user");
        //如果用户是第一次进入，会话中尚未存储user持久化对象，故为null
        if(user==null){
            //查询userTable表中的记录
            String sql="select * from userTable";
            ResultSet rs=sqlsrvdb.executeQuery(sql);				//取得结果集
            try {
                while(rs.next())
                {
                    if((rs.getString("用户名").trim().compareTo(usr)==0)&&(rs.getString("密码").
                            compareTo(pwd)==0)){
                        user=new UserTable();			//创建持久化的JavaBean对象user
                        user.setUsername(rs.getString(1));
                        user.setIdentity(rs.getInt(3));
                        session.setAttribute("user", user);	//把user对象存储在会话中
                        validated=true;				//标识为true表示验证成功通过
                    }
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sqlsrvdb.closeStmt();
        }
        else{
            validated=true;		//该用户在之前已登录过并成功验证，故标识为true表示无须再验了
        }
        if(validated)
        {
            Gson gson = new Gson();
            String result = gson.toJson(user);
            response.getWriter().write(result);
        }
        else {
            Gson gson = new Gson();
            String result = gson.toJson(0);
            response.getWriter().write(result);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
}
