package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("关闭数据库连接池时发生异常", e);
        }
    }
}
