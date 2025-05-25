package dao;

import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TeacherDAO extends BaseDAO {
    private static final Logger logger = LoggerFactory.getLogger(TeacherDAO.class);
    // 验证教师登录
    public boolean validateTeacher(String username, String password) {
        String sql = "select * from teacher where username=? and password=?";
        try (ResultSet rs = executeQuery(sql, username, password)) {
            return rs.next();
        } catch (SQLException e) {
            logger.error("验证教师登录失败：username={}",username,e);
            return false;
        }
    }

    // 获取教师信息
    public Teacher getTeacherByUsername(String username) {
        String sql = "select * from teacher where username=?";
        try(ResultSet rs = executeQuery(sql,username)) {
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setName(rs.getString("name"));
                teacher.setCourse(rs.getString("course"));
                teacher.setUsername(username);
                return teacher;
            }
        } catch (SQLException e) {
            logger.error("获取教师信息失败：username={}",username,e);
        }
        return  null;
    }


    // 获取所有教师
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "select * from teacher";
        try (ResultSet rs = executeQuery(sql)) {
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setName((rs.getString("name")));
                teacher.setCourse(rs.getString("course"));
                teacher.setUsername(rs.getString("username"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            logger.error("获取所有教师信息失败",e);
        }
        return teachers;
    }
}
