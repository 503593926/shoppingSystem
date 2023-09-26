package ui.adminframe.adminframe_jpanel;

import Data.PersonInfo;
import Data.UserInfo;

import javax.swing.*;
import java.awt.*;

public class ResetPassword extends JPanel {
    public ResetPassword() {
        init();
    }

    public void init() {
        // 设置布局
        this.setLayout(null);
        this.setBackground(Color.white);
        // 创建一个id输入框
        JLabel idLabel = new JLabel("用户id:");
        idLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));// 设置字体
        idLabel.setBounds(150, 140, 100, 50);

        JTextField idField = new JTextField();
        idField.setBounds(220, 150, 200, 30);

        // 创建一个标签和一个文本框 输入要重置密码的用户id
        JLabel accountLabel = new JLabel("用户账号:");
        accountLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));// 设置字体
        accountLabel.setBounds(130, 190, 100, 50);

        JTextField accountField = new JTextField();
        accountField.setBounds(220, 200, 200, 30);

        // 创建一个重置密码按钮
        JButton resetButton = new JButton("重置密码");
        resetButton.setBounds(250, 300, 100, 50);
        // 监听重置密码按钮
        resetButton.addActionListener(e -> {
            // 获取用户账号和id
            String account = accountField.getText();
            String id = idField.getText();
            // 至少输入一个
            if (account.equals("") && id.equals("")) {
                JOptionPane.showMessageDialog(null, "请输入用户账号或id", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 获取默认密码
            PersonInfo personInfo = PersonInfo.getInstance();
            String defPassword = personInfo.getDefPassword();

            // 输入了账号
            if (!account.equals("")) {
                // 获取用户数据
                UserInfo userInfo = UserInfo.getInstance();
                // 判断账号是否存在
                if (!personInfo.getAccountToID().containsKey(account)) {
                    JOptionPane.showMessageDialog(null, "账号不存在", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 重置密码
                // 获取用户id
                int idInt = personInfo.getAccountToID().get(account);
                userInfo.getIdToUser().get(idInt).setPassword(defPassword);
                personInfo.getIdToPeron().get(idInt).setPassword(defPassword);
                personInfo.getAccountToPassword().put(personInfo.getIdToPeron().get(idInt).getAccount(), defPassword);
                // 提示重置成功
                JOptionPane.showMessageDialog(null, "重置成功", "提示", JOptionPane.PLAIN_MESSAGE);
            }

            // 输入了id
            if (!id.equals("")) {
                // 获取用户数据
                UserInfo userInfo = UserInfo.getInstance();
                // 判断id是否存在
                if (!personInfo.getIdToPeron().containsKey(Integer.parseInt(id))) {
                    JOptionPane.showMessageDialog(null, "id不存在", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 重置密码
                // 获取用户id
                int idInt = Integer.parseInt(id);
                userInfo.getIdToUser().get(idInt).setPassword(defPassword);
                personInfo.getIdToPeron().get(idInt).setPassword(defPassword);
                personInfo.getAccountToPassword().put(personInfo.getIdToPeron().get(idInt).getAccount(), defPassword);
                // 提示重置成功
                JOptionPane.showMessageDialog(null, "重置成功", "提示", JOptionPane.PLAIN_MESSAGE);
            }
        });

        this.add(idLabel);
        this.add(idField);
        this.add(accountLabel);
        this.add(accountField);
        this.add(resetButton);
    }
}
