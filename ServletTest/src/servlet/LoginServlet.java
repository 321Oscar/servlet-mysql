package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Utils.*;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "登录", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("不支持GET方法");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("utf-8");
		
		BufferedReader read = new BufferedReader(new InputStreamReader(
				request.getInputStream(),"utf-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while((line = read.readLine())!= null) {
			sb.append(line);
		}
		read.close();
		String req = sb.toString();
		System.out.println("first:"+req);
		
		//获取客户端发来的请求，恢复其JSON——>需要客户端发请求时也封装成JSON
		JSONObject object = JSONObject.fromObject(req);
		String type = object.getString("ActionType");
		JSONObject params = object.getJSONObject("params");
		System.out.println(type);

		
		//自定义的结果信息类
		CommonResponse res = new CommonResponse();
		BasicActions actiontype =new BasicActions();
		
		switch (type) {
		case "1"://登录
			res.setresCode(actiontype.login(params));
			break;
		case "2"://注册
			res.setresCode(actiontype.register(params));
			break;
		case "3"://用户关注视频信息
			res = actiontype.uservideos(params);
			break;
		case "4"://用户基本信息
			res = actiontype.userinfos(params);
			break;
		}
		
		//JSON格式转为字符串
		String resStr = JSONObject.fromObject(res).toString();
		System.out.println("second"+resStr);
		//传回客户端
		response.getWriter().append(resStr).flush();
		
	}
	
	
	
	
	
	
	

}
