package dao;

import java.sql.SQLException;
import java.util.List;

import beans.UploadFile;
import utils.JdbcUtils;

public class FileUploadDao {

	private JdbcUtils utils = null;

	public FileUploadDao() {
		utils = new JdbcUtils();
	}
	
	public boolean insert(List<Object> entity) {
		// TODO Auto-generated method stub
		boolean flag = false;
		utils.getConnection();
		String sql = "insert into file(filename) values(?)";
		try {
			flag = utils.updateByPreparedStatement(sql,entity);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			utils.releaseconn();
		}
		return flag;
	}
}
