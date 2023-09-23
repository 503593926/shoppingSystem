package ui.adminframe.adminframe_jpanel;

import Data.CommodityInfo;
import Data.UserInfo;
import Oper.Commodity;
import Oper.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

public class ListALLCommodity extends JPanel{
    public ListALLCommodity() {
        init();
    }

    public void init() {
        this.setLayout(new BorderLayout());
        // 获取商品数据
        CommodityInfo commodityInfo = CommodityInfo.getInstance();
        // 自定义表格model
        MyTableModel model = new MyTableModel(commodityInfo.getIdToCommodity().values());
        // 创建一个表格
        JTable table = new JTable(model);
        table.setSurrendersFocusOnKeystroke(true);
        table.putClientProperty("JTable.autoStartsEdit", true);
        table.putClientProperty("JTable.autoStartsEdit", true);
        table.setDefaultRenderer(Object.class, new CenteredTableCellRenderer()); // 使用自定义渲染器让表格中文字居中显示
        table.setRowHeight(25); // 将所有行的高度设置为25像素
        // 添加鼠标事件监听器到表格
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int columnIndex = table.columnAtPoint(e.getPoint());
                    int rowIndex = table.rowAtPoint(e.getPoint());

                    // 判断是否点击了图片列
                    if (columnIndex == 0 && rowIndex >= 0) {
                        // 打开文件选择器
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        int result = fileChooser.showOpenDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            String imgPath = fileChooser.getSelectedFile().getAbsolutePath();
                            Commodity commodity = model.commodityData.get(rowIndex);
                            commodity.setImg(imgPath);

                            // 更新数据后，通知表格模型数据已更改
                            model.fireTableCellUpdated(rowIndex, columnIndex);
                        }
                    }
                }
            }
        });

        // 添加滚动条
        JScrollPane scrollPane = new JScrollPane(table);

        // 创建搜索按钮
        JButton findButton = new JButton("查找");
        // 监听搜索按钮
        findButton.addActionListener(e -> {
            // 弹出一个对话框，输入要查找的 ID
            String inputTargetId = JOptionPane.showInputDialog(null, "请输入要查找的商品ID", "查找", JOptionPane.PLAIN_MESSAGE);
            // 输入验证
            if (inputTargetId == null)
                return;
            if (!inputTargetId.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "ID必须是一个非负整数", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int targetId = Integer.parseInt(inputTargetId);
            int row;
            for (row = 0; row < table.getRowCount(); row++) {
                int id = (int) table.getValueAt(row, 1); // 获取当前行的 ID 列的值
                if (id == targetId) {
                    // 找到目标行，选择该行
                    table.setRowSelectionInterval(row, row);
                    // 滚动到选中的行
                    table.scrollRectToVisible(table.getCellRect(row, 0, true));
                    break; // 停止搜索
                }
            }
            if (row == table.getRowCount()) {
                JOptionPane.showMessageDialog(null, "没有找到 ID 为 " + targetId + " 的商品", "提示", JOptionPane.ERROR_MESSAGE);
            }

        });

        // 创建删除和添加和修改按钮按钮
        JButton deleteButton = new JButton("删除");
        JButton addButton = new JButton("添加");

        // 监听删除按钮
        deleteButton.addActionListener(e -> {
            // 获取选中的行
            int[] selectedRows = table.getSelectedRows();
            // 如果没有选中行，弹出提示框
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(null, "请先选择要删除的商品", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // 弹出确认对话框
                int option = JOptionPane.showConfirmDialog(null, "确定要删除选中的商品吗？", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // 删除选中的行
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int selectedRow = selectedRows[i];
                        int id = (int) table.getValueAt(selectedRow, 1); // 获取选中行的 ID
                        commodityInfo.getIdToCommodity().remove(id); // 从数据中删除
                        model.commodityData.remove(selectedRow); // 从表格模型中删除
                    }
                    // 通知表格更新
                    model.fireTableDataChanged();
                }
            }
        });

        // 监听添加按钮
        addButton.addActionListener(e -> {
            // 弹出一个对话框，输入要添加的商品信息
            Commodity commodity = showCustomDialog(null);
            if (commodity != null) {
                // 添加到数据中
                commodityInfo.getIdToCommodity().put(commodity.getID(), commodity);
                // 添加到表格模型中
                model.commodityData.add(commodity);
                // 通知表格更新
                model.fireTableDataChanged();
            }
        });

        // 添加和删除按钮在同一行
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(new JLabel("                        "));
        buttonPanel.add(deleteButton);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(findButton, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private static Commodity showCustomDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Custom Input Dialog", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(9, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField purCostField = new JTextField();
        JTextField retailPriceField = new JTextField();
        JTextField manufacturerField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField dateField = new JTextField();

        inputPanel.add(new JLabel("id:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("purCost:"));
        inputPanel.add(purCostField);
        inputPanel.add(new JLabel("retailPrice:"));
        inputPanel.add(retailPriceField);
        inputPanel.add(new JLabel("manufacturer:"));
        inputPanel.add(manufacturerField);
        inputPanel.add(new JLabel("type:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("date:"));
        inputPanel.add(dateField);

        // 添加一个文件选择器用来选择图片
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        inputPanel.add(new JLabel("img:"));
        JButton chooseImgButton = new JButton("选择图片");
        String[] imgPath = {""};
        chooseImgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    imgPath[0] = fileChooser.getSelectedFile().getAbsolutePath();
                }
            }
        });

        inputPanel.add(chooseImgButton);


        JButton okButton = new JButton("OK");
        boolean[] okButtonClicked = {false};
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonClicked[0] = true;
                dialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        if (okButtonClicked[0]) {
            // 从输入框中获取数据，创建一个新的商品对象并返回
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String manufacturer = manufacturerField.getText();
            String date = dateField.getText();
            String type = typeField.getText();
            double purCost = Double.parseDouble(purCostField.getText());
            double retailPrice = Double.parseDouble(retailPriceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String img = imgPath[0];

            return new Commodity(id, name, manufacturer, date, type, purCost, retailPrice, quantity, img);
        }
        else return null;
    }


    private class MyTableModel extends AbstractTableModel {
        private ArrayList<Commodity> commodityData;
        private String[] columnNames = {"图片", "ID", "名称", "进货价", "销售价", "生产厂商", "类型", "数量" , "生产日期"};

        public MyTableModel(Collection<Commodity> userData) {
            this.commodityData = new ArrayList<>(userData);

        }
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public int getRowCount() {
            return commodityData.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Commodity commodity = commodityData.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return new ImageIcon(commodity.getImg());
                case 1:
                    return commodity.getID();
                case 2:
                    return commodity.getName();
                case 3:
                    return commodity.getPurCost();
                case 4:
                    return commodity.getRetailPrice();
                case 5:
                    return commodity.getManufacturer();
                case 6:
                    return commodity.getType();
                case 7:
                    return commodity.getQuantity();
                case 8:
                    return commodity.getDate();
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Commodity commodity = commodityData.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    // 修改图片
                    //commodity.setImg((String) aValue);
                    break;
                case 1:
                    // 如果要修改ID，你可以在这里更新商品的ID属性
                    // commodity.setID((String) aValue);
                    break;
                case 2:
                    // 修改名称
                    commodity.setName((String) aValue);
                    break;
                case 3:
                    // 修改进货价
                    commodity.setPurCost(Double.parseDouble((String) aValue));
                    break;
                case 4:
                    // 修改销售价
                    commodity.setRetailPrice(Double.parseDouble((String) aValue));
                    break;
                case 5:
                    // 修改生产厂商
                    commodity.setManufacturer((String) aValue);
                    break;
                case 6:
                    // 修改类型
                    commodity.setType((String) aValue);
                    break;
                case 7:
                    // 修改数量
                    commodity.setQuantity(Integer.parseInt((String) aValue));
                    break;
                case 8:
                    // 修改生产日期
                    commodity.setDate((String) aValue);
                    break;
                default:
                    break;
            }
            // 更新数据后，通知表格模型数据已更改
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        // 设置单元格为可编辑
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // 图片和id列不可编辑
            return columnIndex != 0 && columnIndex != 1;
        }
    }


    // 设置渲染器 使table中的数据居中显示
    public class CenteredTableCellRenderer extends DefaultTableCellRenderer {
        public CenteredTableCellRenderer() {
            setHorizontalAlignment(JLabel.CENTER); // 设置水平对齐方式为居中
        }
    }
}
