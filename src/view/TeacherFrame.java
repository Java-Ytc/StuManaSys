package view;

import model.Stu;
import model.Teacher;
import service.TeacherService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TeacherFrame extends JFrame {
    private String course;
    private final JTable studentTable;
    private final TeacherService teacherService = new TeacherService();

    public TeacherFrame(String username) {
        Teacher teacher = teacherService.getTeacherByUsername(username);
        if (teacher != null) {
            course = teacher.getCourse();
        }

        setTitle("教师界面 - " + course);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("成绩管理 - " + course));

        studentTable = new JTable(getStudentTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        mainPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private DefaultTableModel getStudentTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"学号", "姓名", course + "成绩"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Integer.class : super.getColumnClass(columnIndex);
            }
        };

        List<Stu> stuList = teacherService.getAllStudents();
        for (Stu student : stuList) {
            Integer score = course.equals("Java") ? student.getJavaScore() : student.getOsScore();
            model.addRow(new Object[]{student.getStuId(), student.getName(), score != null && score != 0 ? score : null});
        }
        return model;
    }

    private void saveScores() {
        if (studentTable.isEditing()) {
            studentTable.getCellEditor().stopCellEditing();
        }

        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        boolean hasError = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            String sid = (String) model.getValueAt(i, 0);
            Object scoreObj = model.getValueAt(i, 2);

            if (scoreObj == null) continue;

            try {
                int score = Integer.parseInt(scoreObj.toString());
                if (score < 0 || score > 100) {
                    hasError = true;
                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行的成绩必须在0-100之间！", "输入错误", JOptionPane.ERROR_MESSAGE);
                } else if (!teacherService.updateStudentScore(sid, course, score)) {
                    hasError = true;
                    JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行的成绩保存失败！", "保存失败", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                hasError = true;
                JOptionPane.showMessageDialog(this, "第 " + (i + 1) + " 行的成绩不是有效的整数！", "格式错误", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!hasError) {
            JOptionPane.showMessageDialog(this, "所有成绩保存成功！");
            studentTable.setModel(getStudentTableModel());
        }
    }

    private void queryBySid() {
        String sid = JOptionPane.showInputDialog(this, "请输入学号：");
        if (sid != null && !sid.isEmpty()) {
            Stu stu = teacherService.getStudentById(sid);
            if (stu != null) {
                DefaultTableModel model = new DefaultTableModel(new String[]{"学号", "姓名", course + "成绩"}, 0) {
                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        return columnIndex == 2 ? Integer.class : super.getColumnClass(columnIndex);
                    }
                };
                Integer score = course.equals("Java") ? stu.getJavaScore() : stu.getOsScore();
                model.addRow(new Object[]{stu.getStuId(), stu.getName(), score != null && score != 0 ? score : null});
                studentTable.setModel(model);
            } else {
                JOptionPane.showMessageDialog(this, "学生不存在");
            }
        }
    }

    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("保存成绩");
        JButton queryBtn = new JButton("按学号查询");

        // 使用 Lambda 表达式替换匿名内部类
        saveBtn.addActionListener(e -> saveScores());
        queryBtn.addActionListener(e -> queryBySid());

        btnPanel.add(saveBtn);
        btnPanel.add(queryBtn);
        return btnPanel;
    }
}
