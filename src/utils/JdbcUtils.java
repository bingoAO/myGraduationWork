package utils;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class JdbcUtils {
private final String USERNAME = "root";
private final String PASSWORD = "sheshihao";
private final String DRIVER = "com.mysql.jdbc.Driver";

private final String URL = "jdbc:mysql://localhost:3306/healer";

private Connection connection;
private PreparedStatement pstmt;
private ResultSet resultSet;

public JdbcUtils(){
	
	try {
		Class.forName(DRIVER);
		System.out.println("register driver success");

	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
}

public Connection getConnection(){
	try {
		System.out.println("connection start......");
		connection=(Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
		System.out.println("connection success");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return connection;
}

public boolean updateByPreparedStatement(String sql,List<Object> params) throws SQLException{
	boolean flag = false;
	int result = -1;
		pstmt = (PreparedStatement) connection.prepareStatement(sql);
	    int index = 1;
	    if(params!=null&&!params.isEmpty()){
	    	for(int i = 0;i<params.size();i++){
	    		pstmt.setObject(index++, params.get(i));
	    	}
	    }
	    result = pstmt.executeUpdate();
	    flag = result>0?true:false;
	return flag;
}

public Map<String,Object> findSimpleResult(String sql,List<Object> params) throws SQLException{
	Map<String,Object> map = new HashMap<String,Object>();
	int index  = 1;
	pstmt = (PreparedStatement) connection.prepareStatement(sql);
	if(params!=null&&!params.isEmpty()){
		for(int i = 0;i<params.size();i++){
			pstmt.setObject(index++, params.get(i));
		}
	}
	resultSet = pstmt.executeQuery();
	
	ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
	
	int clo_len = metaData.getColumnCount();
	
	while(resultSet.next()){
		for(int i=0;i<clo_len;i++){
			String clo_name = metaData.getColumnName(i+1);
			Object clo_value = resultSet.getObject(clo_name);
			if(clo_value == null){
				clo_value="";
			}
			map.put(clo_name, clo_value);
		}
	}
	return map;
}

public List<Map<String,Object>> findMoreResult(String sql,List<Object> params) throws SQLException{
	List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
	//fill the place;
	int index = 1;
	pstmt = (PreparedStatement) connection.prepareStatement(sql);
	if(params!=null&&!params.isEmpty()){
	for(int i=0;i<params.size();i++){
		pstmt.setObject(index, params.get(i));
	}
	}
	
	resultSet = pstmt.executeQuery();
	ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
	
	int clo_len = metaData.getColumnCount();
	while(resultSet.next()){
		Map<String,Object> map = new HashMap<String, Object>();
		
		for(int i=0;i<clo_len;i++){
			String clo_name = metaData.getColumnName(i+1);
			Object clo_value = resultSet.getObject(clo_name);
			if(clo_value == null){
				clo_value = "";
			}
			map.put(clo_name, clo_value);
		}
      list.add(map);
      }
	return list;
}

//relection
public <T> T findSimpleRefResult(String sql,List<Object> params,Class<T> cls) throws Exception{
	T userinfo=null;
	
	int index = 1;
	pstmt=(PreparedStatement) connection.prepareStatement(sql);
	if(params!=null&&!params.isEmpty()){
	for(int i=0;i<params.size();i++){
		pstmt.setObject(index++, params.get(i));
	}
	}
	
	resultSet = pstmt.executeQuery();
	ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
	int clo_len = metaData.getColumnCount();
	
	while(resultSet.next()){
		userinfo = cls.newInstance();
		for(int i = 0;i<clo_len;i++){
			String clo_name = metaData.getColumnName(i+1);
			Object clo_value = resultSet.getObject(clo_name);
			
			Field field = cls.getDeclaredField(clo_name);
			field.setAccessible(true);
			field.set(userinfo, clo_value);
		}
	}
	
	return userinfo;
}

public <T> List<T> findMoreRefResult(String sql,List<Object> params,Class<T> cls) throws Exception{
	List<T> userinfos=new ArrayList<T>();
	
	int index = 1;
	pstmt=(PreparedStatement) connection.prepareStatement(sql);
	if(params!=null&&!params.isEmpty()){
	for(int i=0;i<params.size();i++){
		pstmt.setObject(index++, params.get(i));
	}
	}
	
	resultSet = pstmt.executeQuery();
	ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
	int clo_len = metaData.getColumnCount();
	
	while(resultSet.next()){
		T userinfo = cls.newInstance();
		for(int i = 0;i<clo_len;i++){
			String clo_name = metaData.getColumnName(i+1);
			Object clo_value = resultSet.getObject(clo_name);
			
			Field field = cls.getDeclaredField(clo_name);
			field.setAccessible(true);
			field.set(userinfo, clo_value);
		}
		userinfos.add(userinfo);
	}
	
	return userinfos;
}

public void releaseconn(){
	if(resultSet!=null){
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if(pstmt!=null){
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if(connection!=null){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}



}
