package service;

import dao.StuDAO;
import dao.TeacherDAO;
import model.Stu;
import model.Teacher;

import java.util.List;

public class AdminService {
    private static final StuDAO stuDAO = new StuDAO();
    private static final TeacherDAO teacherDAO = new TeacherDAO();

    // 添加学生
    public boolean addStudent(Stu stu) {
        return stuDAO.addStu(stu) > 0;
    }

    // 获取所有学生
    public List<Stu> getAllStudents() {
        return stuDAO.getAllStu();
    }

    // 获取所有教师
    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAllTeachers();
    }
}
