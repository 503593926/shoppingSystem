package ui.signframe;

import Data.PersonInfo;
import ui.adminframe.AdminFrame;
import ui.userframe.UserFrame;
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
public class SignInFrame extends JFrame {
    public SignInFrame() {
        init();
    }


    public void init() {
        // 设置窗口的属性
        this.setBounds(450, 180, 600, 400); // 位置和大小
        this.setResizable(false); // 不可调整大小
        this.setTitle("登录界面"); // 标题
        this.setVisible(true); // 可见
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

        // 创建带背景图片的容器container
        JPanel container = new JPanel();
        this.setContentPane(container); // 设置把container设为根容器
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边框
        container.setLayout(new BorderLayout()); // 设置布局
        container.setBackground(new Color(0x00cc6a)); // 设置背景颜色

        // 加载图片
        File file = new File("D:\\code\\java\\shopp\\src\\main\\resources\\img\\img_1.png");
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

        // 创建登录按键
        JButton siButton = new JButton("登录");
        // 监听登录按键
        siButton.addActionListener((e -> {
            String account = aTextField.getText();
            String password = pTextField.getText();

            PersonInfo personInfo = PersonInfo.getInstance();
            if (personInfo.signIn(account, password)) {
                // 成功登录
                int id = personInfo.getAccountToID().get(account);
                // 判断身份
                if (personInfo.getIdToPeron().get(id).getStatus() == 0)
                    new AdminFrame(id); // 跳转到管理员界面
                else if (personInfo.getIdToPeron().get(id).getStatus() == 1)
                    new UserFrame(id); // 跳转到用户界面
                this.dispose();
            }
            else {
                // 登陆失败弹出提示框
                JOptionPane.showMessageDialog(this, "账号或密码错误");
            }
        }));
        // 监听注册按键
        JButton suButton = new JButton("注册");
        suButton.addActionListener(e -> {
            new SignUpFrame(); // 跳转到注册界面
            this.dispose();
        });

        // 组装按键部分组件
        Box bBox = Box.createHorizontalBox();
        bBox.add(siButton);
        bBox.add(Box.createHorizontalStrut(200));
        bBox.add(suButton);

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

        // 把组装好的组件添加到面板中
        bgPanel.add(vBox);

        // 把面板添加到容器中
        container.add(bgPanel);
    }

    // 设置容器中所有字体属性
    public void setFontForAllComponents(Container container, Font font) {
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                ((JComponent) component).setFont(font);
            }
            if (component instanceof Container) {
                setFontForAllComponents((Container) component, font);
            }
        }
    }
}
