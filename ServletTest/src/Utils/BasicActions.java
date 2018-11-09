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
		String[] params = new String[8];
		for(int i = 0;i<8;i++) {
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
	
	//根据用户id查找其关注的typeCode，再根据typeCode查找video
	public CommonResponse uservideos(JSONObject object) {
		String name = object.getString("account");
		
		String sqlCodes = String.format("select  video.VideoTitle,video.VideoDes,VideoURL,TypeName \r\n" + 
				"from video,type \r\n" + 
				"where video.VideoType = TypeCode video.VideoType in \r\n" + 
				"(SELECT type.TypeCode \r\n" + 
				"from guanzhu,type \r\n" + 
				"WHERE guanzhu.TypeCode = type.TypeID and guanzhu.UUID = '%s' )", name);
		
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
				response.addListItem(map);
			}
			response.setresCode("00");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
