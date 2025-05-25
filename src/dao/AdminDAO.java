package dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
public class AdminDAO extends BaseDAO {
    private static final Logger logger = LoggerFactory.getLogger(AdminDAO.class);

    // 验证管理员登录
    public boolean validateAdmin(String username,String password){
        String sql = "select * from admin where username=? and password=?";
        try(ResultSet rs = executeQuery(sql,username,password)) {
            return rs.next();
        }catch(SQLException e){
            logger.error("验证管理员失败：username={}",username,e);
            return false;
        }
    }
}
