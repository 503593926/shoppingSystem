package ui.userframe;

import ui.userframe.userframe_jpanel.ChangePasswordPanel;
import ui.userframe.userframe_jpanel.OrderHistoryPanel;
import ui.userframe.userframe_jpanel.CommodityPanel;
import ui.userframe.userframe_jpanel.shoppingCarPanel;
import ui.signframe.SignInFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


/*
用户界面类: 显示用户界面
属性:
    id : 用户id
    WIDTH : 该frame的宽度
    HEIGHT : 该frame的高度
    commodityIcon : 商品图标
    shoppingCartIcon : 购物车图标
    historyOrderIcon : 历史订单图标
    mineIcon : 我的图标
    psManageIcon : 密码管理图标
    changePasswordIcon : 修改密码图标
    resetPasswordIcon : 重置密码图标
方法:
    UserFrame 构造函数
    init 初始化界面
 */
public class UserFrame extends JFrame {
    private int id; // 用户id
    private final int WIDTH = 800; // 该frame的宽度
    private final int HEIGHT = 600; // 该frame的高度
    // 选项图标
    private ImageIcon commodityIcon = new ImageIcon("src\\main\\resources\\icon\\商品.png");
    private ImageIcon shoppingCartIcon = new ImageIcon("src\\main\\resources\\icon\\购物车.png");
    private ImageIcon historyOrderIcon = new ImageIcon("src\\main\\resources\\icon\\历史订单.png");
    private ImageIcon mineIcon = new ImageIcon("src\\main\\resources\\icon\\用户.png");
    private ImageIcon psManageIcon = new ImageIcon("src\\main\\resources\\icon\\密码管理.png");
    private ImageIcon changePasswordIcon = new ImageIcon("src\\main\\resources\\icon\\修改密码.png");
    private ImageIcon resetPasswordIcon = new ImageIcon("src\\main\\resources\\icon\\重置密码.png");


    public UserFrame(int id) {
        this.id = id;
        init();
    }

    private void init() {
        // 创建该frame的根面板
        JPanel container = new JPanel();
        this.setContentPane(container);
        container.setLayout(new BorderLayout()); // 设置布局

        // 设置frame
        // 提取屏幕的宽度和高度
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        this.setBounds((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2, WIDTH, HEIGHT); // 居中显示
        this.setResizable(false); // 不可调整大小

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

        // 创建菜单组件
        JMenuBar jmb = new JMenuBar();
        // 创建设置项
        JMenu jm = new JMenu("设置");
        JMenuItem jmi1 = new JMenuItem("切换账号");
        JMenuItem jmi2 = new JMenuItem("退出");
        // 监听 切换账号 和 退出
        jmi1.addActionListener(e -> {
            new SignInFrame();
            this.dispose();
        });
        jmi2.addActionListener(e -> {
            System.exit(0);
        });
        // 组装设置模块
        jm.add(jmi1);
        jm.add(jmi2);
        jmb.add(jm);

        // 创建分割面板
        JSplitPane sp = new JSplitPane();

        // 分割面板设置
        sp.setDividerLocation(220); // 分割线位置
        sp.setDividerSize(5); // 分割线大小
        sp.setOneTouchExpandable(false); // 不可展开
        sp.setContinuousLayout(true); // 连续布局


        // 创建树形选项
        // 商品
        DefaultMutableTreeNode commodity = new DefaultMutableTreeNode(new NodeData(commodityIcon, "商品"));
        // 购物车
        DefaultMutableTreeNode shoppingCart = new DefaultMutableTreeNode(new NodeData(shoppingCartIcon, "购物车"));
        // 历史订单
        DefaultMutableTreeNode historyOrder = new DefaultMutableTreeNode(new NodeData(historyOrderIcon, "历史订单"));
        // 我的 -- 密码管理 -- (修改密码、重置密码)
        DefaultMutableTreeNode mine = new DefaultMutableTreeNode(new NodeData(mineIcon, "我的"));
        DefaultMutableTreeNode passwordManage = new DefaultMutableTreeNode(new NodeData(psManageIcon, "密码管理"));
        DefaultMutableTreeNode changePassword = new DefaultMutableTreeNode(new NodeData(changePasswordIcon, "修改密码"));
        passwordManage.add(changePassword);
        mine.add(passwordManage);

        //组装选项树
        DefaultMutableTreeNode select = new DefaultMutableTreeNode(new NodeData(commodityIcon, "选项"));
        select.add(commodity);
        select.add(shoppingCart);
        select.add(historyOrder);
        select.add(mine);
        JTree tree = new JTree(select);

        // 设置结点渲染器
        MyRenderer renderer = new MyRenderer();
        renderer.setBackgroundNonSelectionColor(Color.white);  // 结点未选中时背景的颜色
        renderer.setBackgroundSelectionColor(Color.lightGray); // 结点选中时背景的颜色
        tree.setCellRenderer(renderer);

        // 设置当前tree的默认选中
        tree.setSelectionRow(0);

        // 监听叶子结点选项
        tree.addTreeSelectionListener(e -> {
            // 获取当前选中结点对象
            Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();

            if (lastPathComponent.equals(commodity)) {
                // 商品界面
                //sp.setRightComponent();
                sp.setRightComponent(new CommodityPanel(id));
            }
            else if (lastPathComponent.equals(shoppingCart)) {
                // 购物车界面
                //sp.setRightComponent();
                sp.setRightComponent(new shoppingCarPanel(id));
            }
            else if (lastPathComponent.equals(historyOrder)) {
                // 历史订单界面
                sp.setRightComponent(new OrderHistoryPanel(id));
            }
            else if (lastPathComponent.equals(changePassword)) {
                // 修改密码界面
                sp.setRightComponent(new ChangePasswordPanel(id));
                System.out.println("修改密码");
            }
        });


        // 组装选项树
        sp.setLeftComponent(tree);

        // 把组装好的组件添加到根容器中
        container.add(sp);
        this.setJMenuBar(jmb);
        this.setVisible(true);
    }

    // 定义一个NodeData类，用于封装结点数据
    private class NodeData {
        public ImageIcon icon; // 图标
        public String name; // 名称

        public NodeData(ImageIcon icon, String name) {
            this.icon = icon;
            this.name = name;
        }
    }

    // 自定义一个结点渲染器
    private class MyRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            // 获取当前结点
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            // 获取结点数据
            NodeData nodeData = (NodeData) node.getUserObject();
            // 设置
            this.setText(nodeData.name);
            this.setIcon(nodeData.icon);
            // 自定义字体样式
            Font customFont = new Font("微软雅黑", Font.PLAIN, 14);
            this.setFont(customFont);

            return this;
        }
    }
}
