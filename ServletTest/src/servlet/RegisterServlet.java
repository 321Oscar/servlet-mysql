package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Utils.*;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=utf-8");
		
		BufferedReader reader = request.getReader();
		StringBuilder builder = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			builder.append(line);
		}
		
		reader.close();
		String params =builder.toString();
		System.out.println("first:"+params);
		
		
		JSONObject objects = JSONObject.fromObject(params);
		JSONObject jsparams = objects.getJSONObject("params");
		System.out.println("second:"+jsparams);
			
		CommonResponse res = new CommonResponse();
			
		BasicActions register =new BasicActions();
		res.setresCode(register.register(jsparams));

			
		String resStr = JSONObject.fromObject(res).toString();
		System.out.println("finally:"+resStr);
		response.getWriter().append(resStr).flush();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = arg0.getMethod();
		if ("GET".equals(method)) {
			System.out.println("请求方法：GET");
			doGet(arg0, arg1);
		} else if ("POST".equals(method)) {
			System.out.println("请求方法：POST");
			doPost(arg0, arg1);
		} else {
			System.out.println("请求方法分辨失败！");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("Register destroy.");
		super.destroy();
	}

}
