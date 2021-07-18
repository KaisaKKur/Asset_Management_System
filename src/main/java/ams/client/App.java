package ams.client;

import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.alee.api.resource.ClassResource;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.laf.WebLookAndFeel;
import com.alee.managers.language.LanguageLocaleUpdater;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.language.data.Dictionary;

import ams.client.controller.Controller;
import ams.client.login.Login;
import ams.client.skin.light.LightSkin;
import ams.client.welcome.Welcome;

/**
 * 资产管理系统
 */
public class App {
    public static void main(String[] args) {
        /**
         * 防止激活输入法时白屏 这不是必要的，但如果用户选择 frame(Window) 的装饰版，文本框输入是会造成窗体空白 此 L&F 依赖中含有大量旧版
         * jdk 的 java 代码 这是 jdk-1.7 遗留下的 bug
         */
        System.setProperty("sun.java2d.noddraw", "true");
        /**
         * 当放大缩小窗体时，会变透明
         * 这个 bug 至今没有找到，L&F 作者也没有指出问题
         */

        // 登录 ams 系统
        Login.login();
    }

    // Create Swing application
    public static Controller controller = null;

    // 获取本地语言
    static Locale locale = Locale.getDefault();

    public static final void boot() {
        // 委托线程启动资产管理系统
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Install WebLaF as application L&F
                WebLookAndFeel.install(LightSkin.class);

                // 设置软件语言 默认 en
                LanguageManager.setLocale(locale);

                // 引入系统翻译文档库
                LanguageManager
                        .addDictionary(new Dictionary(new ClassResource(App.class, "language/cvr-language.xml")));

                // Registering listener to update current Locale according to language changes
                LanguageManager.addLanguageListener(new LanguageLocaleUpdater());

                // Create Swing application
                setController();

                // 欢迎界面
                new Welcome();
            }
        });
    }

    private static Controller setController() {
        controller = new Controller();
        return controller;
    }

    public static final Controller getController() {
        return controller;
    }

    public static final Locale getLocale() {
        return locale;
    }

    /**
     * 获取默认父组件————顶级容器 JFrame
     * 
     * @return the JFrame
     */
    public static final JFrame getJFrame() {
        return getController().getFrame().getJFrame();
    }

    /**
     * 获取默认父组件————顶级窗格 JPanel
     * 
     * @return the JPanel
     */
    public static final JPanel getJPanel() {
        return getController().getFrame().getJPanel();
    }

    /**
     * 获取默认父组件————顶级窗格 WebDocumentPane
     * 
     * @return the WebDocumentPane
     */
    public static final WebDocumentPane<DocumentData<?>> getWebDocumentPane() {
        return getController().getFrame().getWebDocumentPane();
    }

    public static final void reboot() {
        controller.dispose();
        controller = null;
        boot();
    }
}
