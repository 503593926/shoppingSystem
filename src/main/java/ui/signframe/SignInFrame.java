package ui.signframe;

import Data.PersonInfo;
import ui.userframe.UserFrame;
import ui.customframe.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SignInFrame extends JFrame {
    public SignInFrame() {
        init();
    }


    public void init() {
        // 设置窗口的属性
        this.setBounds(450, 180, 600, 400);
        this.setResizable(false);
        this.setTitle("登录界面");
        this.setVisible(true);

        // 创建带背景图片的容器container
        JPanel container = new JPanel();
        this.setContentPane(container);
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(0x00cc6a));

        // 加载图片
        File file = new File("D:\\code\\java\\shopp\\src\\main\\resources\\img\\img_1.png");
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
        JButton siButton = new JButton("登录");
        siButton.addActionListener((e -> {
            String account = aTextField.getText();
            String password = pTextField.getText();

            PersonInfo personInfo = PersonInfo.getInstance();
            if (personInfo.signIn(account, password)) {
                // 成功登录
                PersonInfo personInfo1 = PersonInfo.getInstance();
                int id = personInfo.getAccountToID().get(account);
                new UserFrame(id);
                this.dispose();
            }
            else {
                // 登陆失败
                JOptionPane.showMessageDialog(this, "密码或账号错误");
            }
        }));
        JButton suButton = new JButton("注册");
        suButton.addActionListener(e -> {
            new SignUpFrame().init();
            this.dispose();
        });

        bBox.add(siButton);
        bBox.add(Box.createHorizontalStrut(200));
        bBox.add(suButton);

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

    public static void main(String[] args) {
        SignInFrame SiFrame = new SignInFrame();
        SiFrame.setVisible(true);
    }

}
