package Utils;

import java.sql.SQLException;


import net.sf.json.JSONObject;

public class UpdateInfos {
	/*
	 * 更新用户信息
	 * */
	public static String UpdateInfo(JSONObject object) {
		String upCode = object.getString("UpdateCode");
		JSONObject params = object.getJSONObject("params");
		String account = params.getString("account");
		String param = params.getString("param");
		
		//结果码
		String resCode="";
		
		String[] items = new String[] {"UserName","sex","age","qq","name","phone","email"};
		
		String updateSql = "";
		
		//判断更改类型码
		switch (upCode) {
		case "1"://更改用户名
			//构造更改用户名的sql语句
			updateSql = getAccountSql(param, account);
			break;
		case "3"://更改性别
			updateSql = getSql(items[1],param, account);
			break;
		case "4"://更改年龄
			updateSql = getSql(items[2],param, account);
			break;
		case "5"://更改qq
			updateSql = getSql(items[3],param, account);
			break;
		case "6"://更改手机
			updateSql = getSql(items[5],param, account);
			break;
		case "7"://更改邮箱
			updateSql = getSql(items[6],param, account);
			break;
		case "8"://更改姓名
			updateSql = getSql(items[4],param, account);
			break;
		}
		System.out.println(updateSql);
		resCode = UpdateAccount(updateSql);
		
		return resCode;
	}
	
	//更改信息的SQL语句
	private static String getSql(String type,String param,String account) {
		return String.format("UPDATE `user` \r\n" + 
				"SET %s = '%s' \r\n" + 
				"where `user`.UserName = '%s'", type,param,account);
	}
	
	//更改用户名的SQL语句
	private static String getAccountSql(String param,String account) {
		return String.format("UPDATE `user` \r\n" + 
				"SET `user`.UserName = '%s' \r\n" + 
				"where `user`.UserName = '%s'", param,account);
	}
	
	private static String UpdateAccount(String sqlupdate) {
		String rescode = "";
		
		DBManager dbManager = DBManager.createInstance();
		dbManager.connectDB();
		
		try {
			if (dbManager.update(sqlupdate)>0) {
				rescode = "11";//更改成功
			}else {
				rescode = "10";//失败
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbManager.close();
		return rescode;
	}
}
