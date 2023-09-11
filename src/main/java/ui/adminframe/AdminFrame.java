package ui.adminframe;

import ui.adminframe.adminframe_jpanel.ListALLCommodity;
import ui.adminframe.adminframe_jpanel.ListAllCustomer;
import ui.adminframe.adminframe_jpanel.ResetPassword;
import ui.signframe.SignInFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/*
客户界面类：

方法 :
 */
public class AdminFrame extends JFrame {
    private int id; // 用户id
    private final int WIDTH = 800; // 该frame的宽度
    private final int HEIGHT = 600; // 该frame的高度
    // 选项图标
    private ImageIcon commodityIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\商品.png");
    private ImageIcon shoppingCartIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\购物车.png");
    private ImageIcon historyOrderIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\历史订单.png");
    private ImageIcon mineIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\用户.png");
    private ImageIcon psManageIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\密码管理.png");
    private ImageIcon changePasswordIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\修改密码.png");
    private ImageIcon resetPasswordIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\重置密码.png");


    public AdminFrame(int id) {
        this.id = id;
        init();
    }

    private void init() {
        // 创建该frame的根面板
        JPanel container = new JPanel();
        this.setContentPane(container);
        container.setLayout(new BorderLayout());

        /* 设置frame */
        // 获取屏幕尺寸
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        // 提取屏幕的宽度和高度
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        // 设置窗口位置及尺寸
        this.setBounds((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2, WIDTH, HEIGHT);
        // 设置窗口尺寸固定
        this.setResizable(false);
        // 设置窗口图标
        //this.setIconImage();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建菜单 */
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

        /* 创建树形选项 */
        // 客户管理   客户管理 -> ( 列出所有客户、删除客户、查询客户信息、重置客户密码 )
        DefaultMutableTreeNode customerManage = new DefaultMutableTreeNode(new NodeData(commodityIcon, "客户管理"));
        DefaultMutableTreeNode listAllCustomer = new DefaultMutableTreeNode(new NodeData(commodityIcon, "列出所有客户"));
        DefaultMutableTreeNode resetCustomerPassword = new DefaultMutableTreeNode(new NodeData(commodityIcon, "重置客户密码"));
        customerManage.add(listAllCustomer);
        customerManage.add(resetCustomerPassword);

        // 商品管理 商品管理 -> ( 列出所有商品信息、添加商品、删除商品、修改商品信息 )
        DefaultMutableTreeNode commodityManage = new DefaultMutableTreeNode(new NodeData(commodityIcon, "商品管理"));
        DefaultMutableTreeNode listAllCommodity = new DefaultMutableTreeNode(new NodeData(commodityIcon, "列出所有商品信息"));
        commodityManage.add(listAllCommodity);

        //组装选项树
        DefaultMutableTreeNode select = new DefaultMutableTreeNode(new NodeData(commodityIcon, "选项"));
        select.add(customerManage);
        select.add(commodityManage);
        JTree tree = new JTree(select);

        // 设置结点渲染器
        MyRenderer renderer = new MyRenderer();
        renderer.setBackgroundNonSelectionColor(Color.white);  // 结点未选中时背景的颜色
        renderer.setBackgroundSelectionColor(Color.lightGray); // 结点选中时背景的颜色
        tree.setCellRenderer(renderer);

        // 设置当前tree的默认选中
        tree.setSelectionRow(0);

        // 监听选项树
        tree.addTreeSelectionListener(e -> {
            // 获取当前选中结点对象
            Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();

            if (lastPathComponent.equals(listAllCustomer)) {
                // 列出所有客户信息
                sp.setRightComponent(new ListAllCustomer());
            }
            else if (lastPathComponent.equals(resetCustomerPassword)) {
                // 重置密码界面
                sp.setRightComponent(new ResetPassword());
            }
            else if (lastPathComponent.equals(listAllCommodity)) {
                // 列出所有商品信息
                sp.setRightComponent(new ListALLCommodity());
            }
        });


        /* 组装各部分 */
        sp.setLeftComponent(tree);

        container.add(sp);
        this.setJMenuBar(jmb);
        this.setVisible(true);
    }

    // 定义一个NodeData类，用于封装结点数据
    private class NodeData {
        public ImageIcon icon;
        public String name;

        public NodeData(ImageIcon icon, String name) {
            this.icon = icon;
            this.name = name;
        }
    }
    public static void main(String[] args) {
        new AdminFrame(1);
    }

    private class MyRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            // 获取当前结点
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)  value;
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

