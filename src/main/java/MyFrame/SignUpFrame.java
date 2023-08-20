package MyFrame;

import Data.PersonInfo;
import Oper.Person;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SignUpFrame extends SignInFrame{

    public SignUpFrame() {
        init();
    }

    public void init() {
        // 设置窗口的属性
        this.setBounds(450, 180, 600, 400);
        this.setResizable(false);
        this.setTitle("注册界面");
        this.setVisible(true);

        // 创建带背景图片的容器container
        JPanel container = new JPanel();
        this.setContentPane(container);
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(0x00cc6a));

        // 加载图片
        File file = new File("D:\\code\\java\\shopp\\src\\main\\resources\\img\\img_2.png");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 设置
        JPanel bgPanel = new BackgroundPanel(image);

        // 组装登录部分组件
        Box vBox = Box.createVerticalBox();

        // 组装账号输入部分组件
        Box aBox = Box.createHorizontalBox();
        JLabel aLabel = new JLabel("账号:");
        JTextField aTextField = new JTextField(12);

        aBox.add(aLabel);
        aBox.add(Box.createHorizontalStrut(25));
        aBox.add(aTextField);

        // 组装密码输入部分组件
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密码:");
        JTextField pTextField = new JTextField(12);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(25));
        pBox.add(pTextField);

        // 组装按键部分组件
        Box bBox = Box.createHorizontalBox();
        JButton sButton = new JButton("提交");
        JButton bButton = new JButton("返回");
        // 提交监听
        sButton.addActionListener((e) -> {
            String account = aTextField.getText();
            String password = pTextField.getText();

            PersonInfo personInfo = PersonInfo.getInstance();
            if (personInfo.getAccountToPassword().containsKey(account)) {
                // 账号已存在
                JOptionPane.showMessageDialog(this, "账号已存在");
            }
            else {
                // 账号不存在
                personInfo.register(account, password, 1);
                JOptionPane.showMessageDialog(this, "注册成功, 即将前往登录页面");
                new SignInFrame().init();
                this.dispose();
            }
        });
        bButton.addActionListener(e -> {
            new SignInFrame().init();
            this.dispose();
        });

        bBox.add(sButton);
        bBox.add(Box.createHorizontalStrut(200));
        bBox.add(bButton);

        vBox.add(Box.createVerticalStrut(70));
        vBox.add(aBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(bBox);

        // 设置面板中所有组件的字体
        Font customFont = new Font("微软雅黑", Font.PLAIN, 22);
        setFontForAllComponents(vBox, customFont);

        bgPanel.add(vBox);

        container.add(bgPanel);
    }

    public static void main(String[] args) {
        SignUpFrame SuFrame = new SignUpFrame();
        SuFrame.setVisible(true);
        PersonInfo personInfo = PersonInfo.getInstance();
        for (String i : personInfo.getAccountToPassword().keySet()) {
            System.out.println(i + " " + personInfo.getAccountToID().get(i));
        }
    }
}
