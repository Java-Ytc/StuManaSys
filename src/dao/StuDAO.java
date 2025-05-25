package dao;

import model.Stu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StuDAO extends BaseDAO {
    private static final Logger logger = LoggerFactory.getLogger(StuDAO.class);

    //验证学生登录
    public boolean validateStu(String stuId, String password) {
        String sql = "select * from student where student_id=? and password=?";
        try (ResultSet rs = executeQuery(sql, stuId, password)) {
            return rs.next();
        } catch (SQLException e) {
            logger.error("验证学生登录失败：stuId={}", stuId, e);
            return false;
        }
    }

    // 添加学生
    public int addStu(Stu stu) {
        String sql = "insert into student(student_id,name,password) values(?,?,?)";
        try {
            return executeUpdate(sql, stu.getStuId(), stu.getName(), stu.getPassword());
        } catch (SQLException e) {
            logger.error("添加学生失败：stuId={}",stu.getStuId(), e);
            return 0;
        }
    }

    // 获取所有学生
    public List<Stu> getAllStu() {
        List<Stu> stuList = new ArrayList<>();
        String sql = "select * from student";
        try (ResultSet rs = executeQuery(sql)) {
            while (rs.next()) {
                Stu stu = new Stu();
                stu.setStuId(rs.getString("student_id"));
                stu.setName(rs.getString("name"));
                stu.setJavaScore(rs.getInt("java_course"));
                stu.setOsScore(rs.getInt("os_course"));
                stuList.add(stu);
            }
        } catch (SQLException e) {
            logger.error("获取所有学生失败", e);
        }
        return stuList;
    }

    // 根据学号获取学生
    public Stu getStuById(String stuId) {
        String sql = "select * from student where student_id=?";
        try (ResultSet rs = executeQuery(sql, stuId)) {
            if (rs.next()) {
                Stu stu = new Stu();
                stu.setStuId(rs.getString("student_id"));
                stu.setName(rs.getString("name"));
                stu.setJavaScore(rs.getInt("java_course"));
                stu.setOsScore(rs.getInt("os_course"));
                return stu;
            }
        } catch (SQLException e) {
            logger.error("获取学生失败:stuId= {}", stuId, e);
        }
        return null;
    }

    // 更新学生成绩
    public int updateScore(String stuId, String course, Integer score) {
        String column = course.equals("Java") ? "java_course" : "os_course";
        String sql = "update student set " + column + "=? where student_id=?";
        try {
            return executeUpdate(sql, score, stuId);
        } catch (SQLException e) {
            logger.error("更新学生成绩失败：stuId={}",stuId, e);
        }
        return 0;
    }
}
