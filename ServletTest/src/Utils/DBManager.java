package Utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBManager {
	//table
	public static final String TABLE_PASSWORD = "user";
	public static final String TABLE_USERIONFO = "user";
	
	//database info
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASS = "mysql123456";
	public static final String URL = "jdbc:mysql://localhost:3306/biyele";
	
	private Connection conn;
	private Statement stmt;
	private static DBManager dbmaneger = null ;

	
	public static DBManager createInstance() {
		if(dbmaneger == null) {
			dbmaneger =new DBManager();
			dbmaneger.initDB();
		}
		return dbmaneger;
	}
	
	//加载驱动
	public void initDB() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//连接数据库，获取句柄+对象
	public void connectDB() {
		try {
			conn = (Connection) DriverManager.getConnection(URL,USER,PASS);
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询
	public ResultSet query(String querysql) throws SQLException{
		ResultSet rSet =null;
		rSet = stmt.executeQuery(querysql);
		return rSet;
	}
	
	//增删改
	public int update(String insertsql) throws SQLException{
		int ret = 0;
		ret = stmt.executeUpdate(insertsql);
		return ret;
	}
	
	//关闭数据库，关闭对象，释放句柄
	public void close() {
		if(conn != null) {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
