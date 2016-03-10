package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import utils.JdbcUtils;
import service.RegisterService;

public class RegisterDao implements RegisterService{
	
	private JdbcUtils utils = null;
	

	public  RegisterDao(){
		utils = new JdbcUtils();
	}
	
	@Override
	public boolean registerUser(List<Object> params) {
		boolean flag = false;
		utils.getConnection();
		String sql = "insert into userinfo(username,password) values(?,?)";
		try {
			flag = utils.updateByPreparedStatement(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			utils.releaseconn();
		}
		return flag;
	}

	/**
	 * 判断用户是否存在
	 */
	@Override
	public boolean isUserExist(List<Object> params) {
		utils.getConnection();
	    String sql = "select * from userinfo where username= ? and password = ?";
		try {
			Map<String,Object> maps = utils.findSimpleResult(sql, params);
			if(maps!=null&&maps.size()!=0)
			return true;
			else return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
