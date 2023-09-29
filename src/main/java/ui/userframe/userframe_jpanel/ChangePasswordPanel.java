package ui.userframe.userframe_jpanel;

import Data.PersonInfo;
import Data.UserInfo;
import Oper.Person;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordPanel extends JPanel {
    private int id;
    public ChangePasswordPanel(int id) {
        this.id = id;
        init();
    }

    public void init() {
        this.setLayout(null);
        this.setBackground(Color.white);

        JLabel oldPasswordLabel = new JLabel("旧密码");
        oldPasswordLabel.setBounds(150, 100, 100, 50);
        this.add(oldPasswordLabel);

        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(200, 100, 200, 50);
        this.add(oldPasswordField);

        JLabel newPasswordLabel = new JLabel("新密码");
        newPasswordLabel.setBounds(150, 200, 100, 50);
        this.add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(200, 200, 200, 50);
        this.add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("确认密码");
        confirmPasswordLabel.setBounds(150, 300, 100, 50);
        this.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(200, 300, 200, 50);
        this.add(confirmPasswordField);

        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(250, 400, 100, 50);
        this.add(confirmButton);

        // 监听confirmButton
        confirmButton.addActionListener(e -> {
            // 旧密码验证
            PersonInfo personInfo = PersonInfo.getInstance();
            Person person = personInfo.getIdToPeron().get(id);
            String nowPassword = person.getPassword();  // 原来的密码
            String oldPassword = new String(oldPasswordField.getPassword()); // 输入的旧密码
            String newPassword = new String(newPasswordField.getPassword()); // 输入的新密码
            String confirmPassword = new String(confirmPasswordField.getPassword()); // 确认密码
            if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
                JOptionPane.showMessageDialog(null, "密码不能为空", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else if (!oldPassword.equals(nowPassword)) {
                JOptionPane.showMessageDialog(null, "旧密码错误", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "两次密码不一致", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // 修改密码
                person.setPassword(newPassword);
                personInfo.getAccountToPassword().put(person.getAccount(), newPassword);
                // 修改userInfo中的密码
                UserInfo userInfo = UserInfo.getInstance();
                userInfo.getIdToUser().get(id).setPassword(newPassword);
                JOptionPane.showMessageDialog(null, "修改成功！你的新密码是:" + newPassword, "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
