import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class text {
    public static void main(String[] args) {
        // 创建一个顶层的窗口
        JFrame frame = new JFrame("多选复选框示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // 创建多个复选框
        JCheckBox checkBox1 = new JCheckBox("选项1");
        JCheckBox checkBox2 = new JCheckBox("选项2");
        JCheckBox checkBox3 = new JCheckBox("选项3");

        // 创建一个标签来显示选择的结果
        JLabel resultLabel = new JLabel("选择的选项：");

        // 创建一个按钮，当点击时显示选择的结果
        JButton showButton = new JButton("显示选择");
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 检查每个复选框的状态并显示选择结果
                String result = "选择的选项：";
                if (checkBox1.isSelected()) {
                    result += checkBox1.getText() + " ";
                }
                if (checkBox2.isSelected()) {
                    result += checkBox2.getText() + " ";
                }
                if (checkBox3.isSelected()) {
                    result += checkBox3.getText() + " ";
                }
                resultLabel.setText(result);
            }
        });

        // 将复选框和按钮添加到窗口中
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(checkBox1);
        panel.add(checkBox2);
        panel.add(checkBox3);
        panel.add(showButton);

        // 将结果标签添加到窗口中
        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);

        // 设置窗口可见
        frame.setVisible(true);
    }
}
