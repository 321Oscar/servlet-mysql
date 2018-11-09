package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostTest
 */
@WebServlet("/PostTest")
public class PostTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account;
		String password;
		
		//获取POST请求参数
		//
//		account = request.getParameter("account");
//		password = request.getParameter("password");
		
		BufferedReader reader = request.getReader();
		String requestStr = reader.readLine();
		System.out.println(requestStr);
		
		HashMap<String, String> requestMap = parseStrToMap(requestStr);
		account = requestMap.get("account");
		password = requestMap.get("password");
		
		//响应
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().append("账号：").append(account).append("\n密码：").append(password);
	}
	
	/*
	 * 针对POST方法中拿到的参数进行解析
	 * */
	
	private HashMap<String,String> parseStrToMap(String str) {
		HashMap<String, String> resultMap = new HashMap<>();
		String[] items = str.split("&");
		String[] itemStrs = new String[2];
		for(String item : items) {
			itemStrs = item.split("=");
			resultMap.put(itemStrs[0],itemStrs[1]);
		}
		return resultMap;
	}

}
