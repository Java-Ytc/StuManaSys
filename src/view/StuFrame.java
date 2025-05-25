package view;

import model.Stu;
import service.StuService;

import javax.swing.*;
import java.awt.*;

public class StuFrame extends JFrame {


    public StuFrame(String sid) {
        setTitle("学生界面 - " + sid);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel sidLabel = new JLabel("学号：");
        JLabel nameLabel = new JLabel("姓名：");
        JLabel javaLabel = new JLabel("Java成绩：");
        JLabel osLabel = new JLabel("操作系统成绩：");

        JTextField sidField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField javaField = new JTextField();
        JTextField osField = new JTextField();

        sidField.setEditable(false);
        nameField.setEditable(false);
        javaField.setEditable(false);
        osField.setEditable(false);

        StuService studentService = new StuService();
        Stu student = studentService.getStudentById(sid);
        if (student != null) {
            sidField.setText(student.getStuId());
            nameField.setText(student.getName());
            javaField.setText(student.getJavaScore() != null && student.getJavaScore() != 0
                    ? String.valueOf(student.getJavaScore()) : "未录入");
            osField.setText(student.getOsScore() != null && student.getOsScore() != 0
                    ? String.valueOf(student.getOsScore()) : "未录入");
        }

        panel.add(sidLabel);
        panel.add(sidField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(javaLabel);
        panel.add(javaField);
        panel.add(osLabel);
        panel.add(osField);

        add(panel);
        setVisible(true);
    }
}
