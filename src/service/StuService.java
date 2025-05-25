package service;

import dao.StuDAO;
import model.Stu;

public class StuService {
    private static final StuDAO stuDAO = new StuDAO();

    // 获取学生信息
    public Stu getStudentById(String studentId) {
        return stuDAO.getStuById(studentId);
    }
}
