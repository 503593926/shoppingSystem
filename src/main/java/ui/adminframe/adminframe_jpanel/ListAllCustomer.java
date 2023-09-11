package ui.adminframe.adminframe_jpanel;

import Data.OrderInfo;
import Data.PersonInfo;
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
            // 输入验证
            if (!inputTargetId.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "ID必须是一个非负整数", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
                        // 从数据中删除
                        PersonInfo personInfo = PersonInfo.getInstance();
                        personInfo.getIdToPeron().remove(id);
                        personInfo.getAccountToPassword().remove(userInfo.getIdToUser().get(id).getAccount());
                        personInfo.getAccountToID().remove(userInfo.getIdToUser().get(id).getAccount());
                        userInfo.getIdToUser().remove(id);
                        // 删除和该用户有关的订单数据
                        OrderInfo orderInfo = OrderInfo.getInstance();
                        if (orderInfo.getUserIdToPayingOrder().containsKey(id))
                            orderInfo.getUserIdToPayingOrder().remove(id);
                        if (orderInfo.getUserIdToPayedOrder().containsKey(id))
                            orderInfo.getUserIdToPayedOrder().remove(id);
                        // 从表格模型中删除
                        model.userData.remove(selectedRow);
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
}
