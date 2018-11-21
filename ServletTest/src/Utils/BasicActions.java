package Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class BasicActions {
	
	/*
	 * 登录动作
	 * @param Json格式的参数，带有登录所需的参数*/
	public String login(JSONObject object) {
		
		//获取账号密码
		String name = object.getString("account");
		String pass = object.getString("password");
		
		//拼接sql语句
		String sqlExit = String.format("select * from %s where UserName = '%s'", 
				DBManager.TABLE_PASSWORD,name);
		
		System.out.println(sqlExit);
		
		//获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		
		
		//设置返回码 0，00,10，11
		String resCode = "0";
		
		//操纵DB对象
		try {		
			if (sql.query(sqlExit).next()) {
				String sqlpass = String.format("select * from %s where UserName = '%s' and pass ='%s'",
						DBManager.TABLE_PASSWORD,name,pass);
				if(sql.query(sqlpass).next()) {
					resCode = "11";//登陆成功
				}else{
					resCode = "10";//密码错误
				}
			}else{
				resCode = "00";//账号不存在
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.close();
		return resCode;
	}
	
	
	/*
	 * 注册动作
	 * @param Json格式的参数，带有注册所需的参数*/
	public String register(JSONObject object) {
		//获取账号，密码,名字，性别，年龄，手机，qq，邮箱
		String[] params = new String[2];
		for(int i = 0;i<2;i++) {
			String code = Integer.toString(i);
			params[i] = object.getString(code);
		}
				
		//拼接sql语句
		String sqlExit = String.format("select * from %s where UserName = '%s'", 
						DBManager.TABLE_PASSWORD,params[0]);
				
		//获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
								
		//设置返回码 0，00,10，11
		String resCode = "0";
				
		//操纵DB对象
		try {		
			if (sql.query(sqlExit).next()) {
				resCode = "10";//已存在			
			}else{
				String sqlinsert = String.format("insert into %s (UserName,pass) values('%s','%s')",
					DBManager.TABLE_PASSWORD,params[0],params[1]);
				System.out.println(sqlinsert);
				if(sql.update(sqlinsert) > 0) {
					resCode = "11";//注册成功
				}else {
					resCode = "01";//注册失败
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.close();
		return resCode;
	}
	
	/*
	 * 取出所有的视频 
	 * */
	public CommonResponse allVideos() {
		String sqlCodes = "select VideoTitle,Videodes,VideoURL,TypeName,ImgURL from video,type\r\n" + 
				"WHERE video.VideoType = type.TypeCode" ;
		System.out.println(sqlCodes);
		
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		CommonResponse response = new CommonResponse();
		try {
			ResultSet resultSet = sql.query(sqlCodes);
			while(resultSet.next()) {
				HashMap<String, String> map = new HashMap<>();
				map.put("title", resultSet.getString("VideoTitle"));
				map.put("des", resultSet.getString("VideoDes"));
				map.put("url", resultSet.getString("VideoURL"));
				map.put("type", resultSet.getString("TypeName"));
				map.put("imgurl", resultSet.getString("ImgURL"));
				response.addListItem(map);
			}
			response.setresCode("11");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.close();
		return response;
	}
	
	/*查询用户的基本信息
	 * 
	 * @param object 参数
	 * */
	public CommonResponse userinfos(JSONObject object) {
		String name = object.getString("account");
		
		String sqlCodes = String.format("SELECT * from %s where `user`.UserName = '%s'",DBManager.TABLE_PASSWORD, name);
		System.out.println(sqlCodes);
		
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		CommonResponse response = new CommonResponse();
		try {
			ResultSet resultSet = sql.query(sqlCodes);
			while(resultSet.next()) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", resultSet.getString("name"));
				map.put("age", resultSet.getString("age"));
				map.put("sex", resultSet.getString("sex"));
				map.put("phone", resultSet.getString("phone"));
				map.put("qq", resultSet.getString("qq"));
				map.put("email", resultSet.getString("email"));
				response.addListItem(map);
			}
			response.setresCode("11");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.close();
		return response;
	}
	
	/*
	 * 查询用户关注的类别
	 * 
	 * @param
	 * */
	public CommonResponse GuanzhuType(JSONObject object) {
		String name = object.getString("account");
		
		String sqlquery = String.format("SELECT TypeName,TypeDes,TypeImg  \r\n" + 
				"from guanzhu,type,`user`\r\n" + 
				"WHERE guanzhu.TypeCode = type.TypeID and guanzhu.UUID= `user`.UUID and `user`.UserName = '%s'", name);
		System.out.println(sqlquery);
		
		DBManager dbManager = new DBManager();
		dbManager.connectDB();
		
		CommonResponse response = new CommonResponse();
		response.setresCode("00");
		try {
			ResultSet resultSet = dbManager.query(sqlquery);
			while(resultSet.next()) {
				HashMap<String, String> data = new HashMap<>();
				data.put("name", resultSet.getString("TypeName"));
				data.put("des", resultSet.getString("TypeDes"));
				data.put("url", resultSet.getString("TypeImg"));
				response.addListItem(data);
			}
			response.setresCode("11");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbManager.close();
		
		return response;
	}
	
	/*
	 * 查询用户屏蔽的类别
	 * */
	
	/*
	 * 更新用户密码
	 * */
	public String UpdatePass(JSONObject object) {
		String name = object.getString("account");
		String oldpsw = object.getString("oldpass");
		String newpsw = object.getString("newpass");
		
		
		String sqlquery = String.format("SELECT `user`.`name` \r\n" + 
				"from `user` \r\n" + 
				"where `user`.UserName = '%s' and `user`.pass ='%s'", name,oldpsw);
		
		DBManager dbManager = DBManager.createInstance();
		dbManager.connectDB();

		String resCode = "00";
				
		try {		
			if (dbManager.query(sqlquery).next()) {
				String sqlupdate = String.format("UPDATE `user` SET pass = '%s'\r\n" + 
						"where `user`.UserName = '%s'",newpsw,name);
				System.out.println("---修改密码----"+sqlupdate);
				if(dbManager.update(sqlupdate)>0) {
					resCode = "11";//修改成功
				}else{
					resCode = "10";//修改失败
				}
			}else{
				resCode = "01";//密码错误
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resCode;
	}
}
