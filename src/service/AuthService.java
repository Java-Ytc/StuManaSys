package service;

import dao.AdminDAO;
import dao.StuDAO;
import dao.TeacherDAO;

public class AuthService {
    private static final AdminDAO adminDAO = new AdminDAO();
    private static final TeacherDAO teacherDAO = new TeacherDAO();
    private static final StuDAO stuDAO = new StuDAO();

    // 验证管理员登录
    public boolean authAdmin(String username,String password) {
        return adminDAO.validateAdmin(username,password);
    }

    // 验证教师登录
    public boolean authenticateTeacher(String username, String password) {
        return teacherDAO.validateTeacher(username, password);
    }

    // 验证学生登录
    public boolean authenticateStudent(String studentId, String password) {
        return stuDAO.validateStu(studentId, password);
    }
}
