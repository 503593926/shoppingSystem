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
        // 创建一个标签和一个文本框 输入要重置密码的用户id
        JLabel idLabel = new JLabel("用户id:");
        idLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));// 设置字体
        idLabel.setBounds(130, 190, 100, 50);

        JTextField idField = new JTextField();
        idField.setBounds(200, 200, 200, 30);

        // 创建一个重置密码按钮
        JButton resetButton = new JButton("重置密码");
        resetButton.setBounds(250, 300, 100, 50);
        // 监听重置密码按钮
        resetButton.addActionListener(e -> {
            // 获取用户id
            String id = idField.getText();
            // 输入验证
            if (!id.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "ID必须是一个非负整数", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 获取默认密码
            PersonInfo personInfo = PersonInfo.getInstance();
            String defPassword = personInfo.getDefPassword();
            // 重置密码
            int idInt = Integer.parseInt(id);
            // 获取用户数据
            UserInfo userInfo = UserInfo.getInstance();
            // 判断id是否存在
            if (!personInfo.getIdToPeron().containsKey(idInt) || !userInfo.getIdToUser().containsKey(idInt)) {
                JOptionPane.showMessageDialog(null, "ID不存在", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 重置密码
            userInfo.getIdToUser().get(idInt).setPassword(defPassword);
            personInfo.getIdToPeron().get(idInt).setPassword(defPassword);
            personInfo.getAccountToPassword().put(personInfo.getIdToPeron().get(idInt).getAccount(), defPassword);
            // 提示重置成功
            JOptionPane.showMessageDialog(null, "重置成功", "提示", JOptionPane.PLAIN_MESSAGE);
        });

        this.add(idLabel);
        this.add(idField);
        this.add(resetButton);
    }
}
