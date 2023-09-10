package ui.signframe;

import Data.PersonInfo;
import ui.customframe.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
登录界面类: 显示登录界面
属性:
    filePath : excel数据文件的地址
方法:
    SignInFrame 构造函数
    init 初始化界面
 */
public class SignUpFrame extends SignInFrame {

    public SignUpFrame() {
        init();
    }

    public void init() {
        // 设置窗口的属性
        this.setBounds(450, 180, 600, 400); // 设置位置和大小
        this.setResizable(false); // 不可调整大小
        this.setTitle("注册界面"); // 设置标题
        this.setVisible(true); // 可见
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

        // 创建带背景图片的容器container
        JPanel container = new JPanel(); // 创建根容器
        this.setContentPane(container); // 设置把container设为根容器
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边框
        container.setLayout(new BorderLayout()); // 设置布局
        container.setBackground(new Color(0x00cc6a)); // 设置背景颜色

        // 加载图片
        File file = new File("D:\\code\\java\\shopp\\src\\main\\resources\\img\\img_2.png");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 创建带有背景图片的面板
        JPanel bgPanel = new BackgroundPanel(image);

        // 创建账号输入部分组件
        JLabel aLabel = new JLabel("账号:");
        JTextField aTextField = new JTextField(12);

        // 组装账号输入部分组件
        Box aBox = Box.createHorizontalBox();
        aBox.add(aLabel);
        aBox.add(Box.createHorizontalStrut(25));
        aBox.add(aTextField);

        // 创建密码输入部分组件
        JLabel pLabel = new JLabel("密码:");
        JTextField pTextField = new JTextField(12);

        // 组装密码输入部分组件
        Box pBox = Box.createHorizontalBox();
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(25));
        pBox.add(pTextField);

        // 创建按键部分组件
        JButton sButton = new JButton("提交");
        JButton bButton = new JButton("返回");
        // 监听提交按钮
        sButton.addActionListener((e) -> {
            String account = aTextField.getText(); // 获取输入的账号
            String password = pTextField.getText(); // 获取输入的密码

            PersonInfo personInfo = PersonInfo.getInstance(); // 获取PersonInfo实例
            // 判断账号是否存在
            if (personInfo.getAccountToPassword().containsKey(account)) {
                // 账号已存在
                JOptionPane.showMessageDialog(this, "账号已存在");
            }
            else {
                // 账号不存在
                personInfo.register(account, password, 1); // 注册账号
                JOptionPane.showMessageDialog(this, "注册成功, 即将前往登录页面");
                new SignInFrame();
                this.dispose();
            }
        });
        // 监听返回按钮
        bButton.addActionListener(e -> {
            new SignInFrame();
            this.dispose();
        });

        // 组装按键部分组件
        Box bBox = Box.createHorizontalBox();
        bBox.add(sButton);
        bBox.add(Box.createHorizontalStrut(200));
        bBox.add(bButton);

        // 组装登录部分组件
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(70));
        vBox.add(aBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(bBox);

        // 设置面板中所有组件的字体
        Font customFont = new Font("微软雅黑", Font.PLAIN, 22);
        setFontForAllComponents(vBox, customFont);

        // 把组装好的组件添加到带有背景图片的面板中
        bgPanel.add(vBox);

        // 把带有背景图片的面板添加到根容器中
        container.add(bgPanel);
    }
}
