package service;

import dao.StuDAO;
import dao.TeacherDAO;
import model.Stu;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);
    private static final StuDAO stuDAO = new StuDAO();
    private static final TeacherDAO teacherDAO = new TeacherDAO();

    // 获取教师信息
    public Teacher getTeacherByUsername(String username) {
        return teacherDAO.getTeacherByUsername(username);
    }

    // 获取所有学生
    public List<Stu> getAllStudents() {
        return stuDAO.getAllStu();
    }

    // 更新学生成绩
    public boolean updateStudentScore(String studentId, String course, Integer score) {
        if (score < 0 || score > 100) {
            logger.warn("尝试更新学生 {} 的 {} 课程成绩为无效分数: {}", studentId, course, score);
            return false;
        }
        return stuDAO.updateScore(studentId, course, score) > 0;
    }

    // 根据学号获取学生
    public Stu getStudentById(String stuId) {
        return stuDAO.getStuById(stuId);
    }
}
