package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet(description ="学习Servlet创建的" ,urlPatterns= {"/FirstServlet"}, initParams= {
		@WebInitParam(name ="userName", value ="abd",description="用户姓名")
} )
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FirstServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("\n初始化参数userName = "+getInitParameter("userName"));

		String account = request.getParameter("account");//从request 中获取名为account的参数的值
		String password = request.getParameter("password");
		System.out.println("account:"+ account+"\npassword:"+password);
		
		String result = "";
//		if("abc".equals(account)
//				&& "123".equals(password)) {
//			result = "Login success!";
//		}else {
//			result = "Sorry! Account or password error";
//		}
		
		if("王x".equals(account)
				&& "杰x".equals(password)) {
			result = "Login success!成功了";
		}else {
			result = "Sorry! Account or password error，错了";
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.println(result);
		pw.flush();                      
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
