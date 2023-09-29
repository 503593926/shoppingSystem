import Data.PersonInfo;
import Data.Port;
import ui.signframe.SignInFrame;

public class Main {
    public static void main(String[] args) {
        // 创建登录界面
        Port.importData();
        new SignInFrame();
        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 在进程结束前执行导出数据的操作
            Port.exportData();
        }));
    }
}
