package Utils;

import java.util.ArrayList;
import java.util.HashMap;


public class CommonResponse {

	private String resCode;
	
	private HashMap<String, String> property;
	
	private ArrayList<HashMap<String, String>> list;
	
	public CommonResponse () {		
		super();
		resCode="";
		property = new HashMap<String,String>();
		list = new ArrayList<HashMap<String,String>>();
	}
	
	public void setResult(String resCode) {
		this.resCode = resCode;
	}
	
	public String getResCode() {
		return resCode;
	}
	
	public void addpro(HashMap<String, String> property) {
		this.property = property;
	}
	
	public void setresCode(String resCode) {
		this.resCode = resCode;
	}
	
	public HashMap<String, String> getProperty() {
		return property;
	}
	
	public void addListItem(HashMap<String, String> map) {
		list.add(map);
	}
	
	public ArrayList<HashMap<String, String>> getList() {
		return list;
	}
	
}
