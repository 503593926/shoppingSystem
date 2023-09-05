import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class text1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Multi-Select Checkbox List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 创建一个列表模型
        DefaultListModel<String> listModel = new DefaultListModel<>();

        // 向列表模型添加数据
        listModel.addElement("选项1");
        listModel.addElement("选项2");
        listModel.addElement("选项3");
        listModel.addElement("选项4");
        listModel.addElement("选项5");

        // 创建一个 JList 并设置其模型
        JList<String> checkBoxList = new JList<>(listModel);

        // 使用自定义渲染器，将复选框添加到每个单元格
        checkBoxList.setCellRenderer(new CheckBoxListCellRenderer());

        // 设置选择模式为多选
        checkBoxList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // 添加 JList 到面板
        panel.add(new JScrollPane(checkBoxList), BorderLayout.CENTER);

        // 创建一个标签用于显示选择结果
        JLabel resultLabel = new JLabel("选择的选项：");
        panel.add(resultLabel, BorderLayout.SOUTH);

        // 添加选择事件监听器
        checkBoxList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 处理选择事件，更新结果标签
                StringBuilder selectedOptions = new StringBuilder("选择的选项：");
                for (String selectedValue : checkBoxList.getSelectedValuesList()) {
                    selectedOptions.append(selectedValue).append(" ");
                }
                resultLabel.setText(selectedOptions.toString());
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}

class CheckBoxListCellRenderer extends JCheckBox implements ListCellRenderer<String> {
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value);
        setSelected(isSelected);
        setEnabled(list.isEnabled());
        return this;
    }
}
