package view;

import model.Stu;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AdminService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(AdminFrame.class);

    private final JTable studentTable;
    private final AdminService adminService = new AdminService();

    public AdminFrame() {
        setTitle("管理员界面");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 学生管理面板（允许添加但禁止编辑表格）
        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.setBorder(BorderFactory.createTitledBorder("学生信息管理"));

        // 创建只读学生表格
        studentTable = new JTable(getStudentTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有单元格不可编辑
            }
        };
        studentPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // 添加学生按钮和刷新按钮
        JPanel buttonPanel = new JPanel();

        JButton addStudentBtn = new JButton("添加学生");
        addStudentBtn.addActionListener(e -> addStudentDialog());
        buttonPanel.add(addStudentBtn);

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> studentTable.setModel(getStudentTableModel()));
        buttonPanel.add(refreshBtn);

        studentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 教师信息面板（完全只读）
        JTable teacherTable;
        JPanel teacherPanel = new JPanel(new BorderLayout());
        teacherPanel.setBorder(BorderFactory.createTitledBorder("教师信息查看"));

        // 创建只读教师表格
        teacherTable = new JTable(getTeacherTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有单元格不可编辑
            }
        };
        teacherPanel.add(new JScrollPane(teacherTable), BorderLayout.CENTER);

        // 主布局
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, studentPanel, teacherPanel);
        splitPane.setDividerLocation(0.7);
        splitPane.setResizeWeight(0.7);
        add(splitPane);

        setVisible(true);
    }

    private DefaultTableModel getStudentTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"学号", "姓名", "Java成绩", "OS成绩"}, 0);
        try {
            List<Stu> students = adminService.getAllStudents();
            for (Stu stu : students) {
                model.addRow(new Object[]{
                        stu.getStuId(),
                        stu.getName(),
                        stu.getJavaScore(),
                        stu.getOsScore()
                });
            }
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "没有找到学生数据");
            }
        } catch (Exception e) {
            logger.error("加载学生信息失败", e);
            JOptionPane.showMessageDialog(this, "加载学生信息失败：" + e.getMessage());
        }
        return model;
    }

    private DefaultTableModel getTeacherTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"教师ID", "姓名", "课程"}, 0);
        try {
            List<Teacher> teachers = adminService.getAllTeachers();
            for (Teacher teacher : teachers) {
                model.addRow(new Object[]{
                        teacher.getTeacherId(),
                        teacher.getName(),
                        teacher.getCourse()
                });
            }
            if (teachers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "没有找到教师数据");
            }
        } catch (Exception e) {
            logger.error("加载教师信息失败", e);
            JOptionPane.showMessageDialog(this, "加载教师信息失败：" + e.getMessage());
        }
        return model;
    }

    private void addStudentDialog() {
        JTextField sidField = new JTextField();
        JTextField nameField = new JTextField();
        JPasswordField pwdField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("学号："));
        panel.add(sidField);
        panel.add(new JLabel("姓名："));
        panel.add(nameField);
        panel.add(new JLabel("密码："));
        panel.add(pwdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加学生", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String sid = sidField.getText();
            String name = nameField.getText();
            String pwd = new String(pwdField.getPassword());
            if (sid.isEmpty() || name.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息");
                return;
            }

            Stu stu = new Stu(sid, name, pwd);
            if (adminService.addStudent(stu)) {
                JOptionPane.showMessageDialog(this, "添加成功");
                studentTable.setModel(getStudentTableModel()); // 刷新表格
            } else {
                JOptionPane.showMessageDialog(this, "添加失败");
            }
        }
    }
}
