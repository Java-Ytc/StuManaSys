package view;

import service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private final JComboBox<String> userTypeCombo;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("学生信息管理系统登录");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        userTypeCombo = new JComboBox<>(new String[]{"管理员", "教师", "学生"});
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("用户类型："));
        panel.add(userTypeCombo);
        panel.add(new JLabel("用户名："));
        panel.add(usernameField);
        panel.add(new JLabel("密   码："));
        panel.add(passwordField);

        JButton loginBtn = new JButton("登录");
        loginBtn.addActionListener((ActionEvent e) -> {
            String userType = (String) userTypeCombo.getSelectedItem();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // 增加非空校验（理论上 JComboBox 不会返回 null，但防御性编程）
            if (userType == null) {
                JOptionPane.showMessageDialog(null, "请选择用户类型");
                return;
            }

            try {
                switch (userType) {
                    case "管理员":
                        if (authService.authAdmin(username, password)) {
                            new AdminFrame();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "用户名或密码错误");
                        }
                        break;
                    case "教师":
                        if (authService.authenticateTeacher(username, password)) {
                            new TeacherFrame(username);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "用户名或密码错误");
                        }
                        break;
                    case "学生":
                        if (authService.authenticateStudent(username, password)) {
                            new StuFrame(username);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "用户名或密码错误");
                        }
                        break;
                    default:
                        // 处理意外值（防御性代码）
                        JOptionPane.showMessageDialog(null, "未知用户类型");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "登录失败：" + ex.getMessage());
            }
        });

        add(panel, BorderLayout.CENTER);
        add(loginBtn, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
