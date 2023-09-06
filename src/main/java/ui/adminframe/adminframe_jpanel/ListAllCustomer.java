package ui.adminframe.adminframe_jpanel;

import Data.UserInfo;
import Oper.Commodity;
import Oper.User;
import ui.customframe.MyPic;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ListAllCustomer extends JPanel {
    public ListAllCustomer() {
        init();
    }

    public void init() {
        this.setLayout(new BorderLayout());
        // 获取用户数据
        UserInfo userInfo = UserInfo.getInstance();
        // 自定义表格model
        MyTableModel model = new MyTableModel(userInfo.getIdToUser().values());
        // 创建一个表格
        JTable table = new JTable(model);
        table.setDefaultRenderer(Object.class, new CenteredTableCellRenderer()); // 使用自定义渲染器让表格中文字居中显示
        table.setRowHeight(25); // 将所有行的高度设置为25像素
        // 添加滚动条
        JScrollPane scrollPane = new JScrollPane(table);

        // 创建搜索按钮
        JButton findButton = new JButton("查找");
        // 监听搜索按钮
        findButton.addActionListener(e -> {
            // 弹出一个对话框，输入要查找的 ID
            String inputTargetId = JOptionPane.showInputDialog(null, "请输入要查找的ID", "查找", JOptionPane.PLAIN_MESSAGE);
            // 把targetid转换为int类型
            int targetId = Integer.parseInt(inputTargetId);
            int row;
            for (row = 0; row < table.getRowCount(); row++) {
                int id = (int) table.getValueAt(row, 0); // 获取当前行的 ID 列的值
                if (id == targetId) {
                    // 找到目标行，选择该行
                    table.setRowSelectionInterval(row, row);
                    // 滚动到选中的行
                    table.scrollRectToVisible(table.getCellRect(row, 0, true));
                    break; // 停止搜索
                }
            }
           if (row == table.getRowCount()) {
               JOptionPane.showMessageDialog(null, "没有找到 ID 为 " + targetId + " 的客户", "提示", JOptionPane.ERROR_MESSAGE);
           }

        });

        // 创建删除按钮
        JButton deleteButton = new JButton("删除");
        // 监听删除按钮
        deleteButton.addActionListener(e -> {
            // 获取选中的行
            int[] selectedRows = table.getSelectedRows();
            // 如果没有选中行，弹出提示框
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(null, "请先选择要删除的客户", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // 弹出确认对话框
                int option = JOptionPane.showConfirmDialog(null, "确定要删除选中的客户吗？", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // 删除选中的行
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int selectedRow = selectedRows[i];
                        int id = (int) table.getValueAt(selectedRow, 0); // 获取选中行的 ID
                        userInfo.getIdToUser().remove(id); // 从数据中删除
                        model.userData.remove(selectedRow); // 从表格模型中删除
                    }
                    // 通知表格更新
                    model.fireTableDataChanged();
                }
            }
        });

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(findButton, BorderLayout.NORTH);
        this.add(deleteButton, BorderLayout.SOUTH);
    }

    private class MyTableModel extends AbstractTableModel {
        private ArrayList<User> userData;
        private String[] columnNames = {"ID", "用户名", "用户级别", "注册时间", "累计消费总额", "手机号", "邮箱"};

        public MyTableModel(Collection<User> userData) {
            this.userData = new ArrayList<>(userData);

        }
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public int getRowCount() {
            return userData.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            User user = userData.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return user.getID();
                case 1:
                    return user.getAccount();
                case 2:
                    return user.getLevel();
                case 3:
                    return user.getTime();
                case 4:
                    return user.getConsumption();
                case 5:
                    return user.getPhone();
                case 6:
                    return user.getEmail();
                default:
                    return null;
            }
        }

//        @Override
//        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//            // 当单元格的值被修改时，更新对应的 Person 对象
//            User user = userData.get(rowIndex);
//            switch (columnIndex) {
//                case 0:
//                    user.setName((String) aValue);
//                    break;
//                case 1:
//                    user.setAge((int) aValue);
//                    break;
//                case 2:
//                    user.setGender((String) aValue);
//                    break;
//                default:
//                    break;
//            }
//            fireTableCellUpdated(rowIndex, columnIndex);
//        }
    }

    public class CenteredTableCellRenderer extends DefaultTableCellRenderer {
        public CenteredTableCellRenderer() {
            setHorizontalAlignment(JLabel.CENTER); // 设置水平对齐方式为居中
        }
    }

//    public void init() {
//        // 列出所有客户信息 包括客户ID、用户名、用户级别、注册时间、累计消费总额、手机号、邮箱
//        // 获取用户数据
//        UserInfo userInfo = UserInfo.getInstance();
//        // 把用户数据添加到UserModel中
//        DefaultListModel<User> userModel = new DefaultListModel<>();
//        userModel.addAll(userInfo.getIdToUser().values());
//        // 创建list
//        JList<User> list = new JList<>(userModel);
//        // 设置list的大小
//        list.setPreferredSize(new Dimension(600, 200));
//        // 设置list的渲染器
//        list.setCellRenderer(new myListCellRenderer());
//        list.setFixedCellHeight(120); // 设置单元格的高度为120像素
//        // 为list添加滚动条
//        JScrollPane scrollList = new JScrollPane(list);
//        this.add(scrollList);
//    }

    // 自定义list的渲染器
    private class myListCellRenderer implements ListCellRenderer<User> {

        JPanel panel = new JPanel(); // 用来放置所有组件
        JPanel panel1 = new JPanel(new BorderLayout());  // 放置客户id、用户名、用户级别
        JPanel panel2 = new JPanel(new BorderLayout());  // 放置注册时间、累计消费总额
        JPanel panel3 = new JPanel(new BorderLayout());  // 放置手机号、邮箱
        JLabel idLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel levelLabel = new JLabel();
        JLabel registerTimeLabel = new JLabel();
        JLabel totalConsumptionLabel = new JLabel();
        JLabel phoneLabel = new JLabel();
        JLabel emailLabel = new JLabel();

        public myListCellRenderer() {
            // 设置面板布局
            panel.setLayout(new BorderLayout());
            // 获取list的长宽
            int WIDTH = getWidth();
            int HEIGHT = getHeight();
            // 设置panel的长宽
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            // 添加边框
            Border emptyBorder = BorderFactory.createEmptyBorder(5, 2, 5, 0);   // 空边框
            // 创建一个带颜色的边框
            Color borderColor = new Color(0, 0, 0); // 指定边框颜色为红色
            int borderWidth = 1; // 指定边框宽度为2像素
            Border lineBorder = new LineBorder(borderColor, borderWidth);
            // 创建一个复合边框，包含空白边框和有颜色的边框
            Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
            panel.setBorder(compoundBorder);

            // 初始化标签，不需要每次都创建
            panel1.setLayout(new BorderLayout());
            panel1.add(idLabel, BorderLayout.WEST);
            panel1.add(nameLabel, BorderLayout.CENTER);
            panel1.add(levelLabel, BorderLayout.EAST);

            panel2.setLayout(new BorderLayout());
            panel2.add(registerTimeLabel, BorderLayout.WEST);
            panel2.add(totalConsumptionLabel, BorderLayout.EAST);

            panel3.setLayout(new BorderLayout());
            panel3.add(phoneLabel, BorderLayout.WEST);
            panel3.add(emailLabel, BorderLayout.EAST);

            // 添加面板到主面板
            panel.add(panel1, BorderLayout.NORTH);
            panel.add(panel2, BorderLayout.CENTER);
            panel.add(panel3, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
            // 更新标签内容
            idLabel.setText("客户ID：" + value.getID());
            nameLabel.setText("用户名：" + value.getAccount());
            levelLabel.setText("用户级别：" + value.getLevel());
            registerTimeLabel.setText("注册时间：" + value.getTime());
            totalConsumptionLabel.setText("累计消费总额：" + value.getConsumption());
            phoneLabel.setText("手机号：" + value.getPhone());
            emailLabel.setText("邮箱：" + value.getEmail());

            return panel;
        }
    }



}
