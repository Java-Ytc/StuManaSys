package dao;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {
    protected ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection con = DBUtil.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        return pstmt.executeQuery();
    }

    //通用更新方法
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        Connection con = DBUtil.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        }
    }
}
