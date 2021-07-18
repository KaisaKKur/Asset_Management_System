package ams.client.frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.alee.api.annotations.NotNull;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.managers.language.UILanguageManager;
import com.alee.managers.style.StyleId;
import com.alee.utils.swing.Customizer;

public class Frame {
    // 创建顶级容器
    private final JFrame mJFrame = new JFrame();
    // 创建顶级窗格
    private final JPanel mJPanel = new JPanel();
    // 创建顶级窗格下的默认文档窗格 初始化默认样式
    private final WebDocumentPane<DocumentData<?>> mWebDocumentPane = new WebDocumentPane<>(StyleId.documentpane,
            new Customizer<WebTabbedPane>() {
                @Override
                public void customize(@NotNull final WebTabbedPane tabbedPane) {
                    // 滚轮模式
                    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                    // 顶部横排
                    tabbedPane.setTabPlacement(JTabbedPane.TOP);
                }
            });
  
    public Frame() {
        // 设置自定义顶级容器
        setJFrame();
    }

    /**
     * 设置自定义顶级容器
     */
    private final void setJFrame() {
        // 导入皮肤
        mJFrame.getRootPane().putClientProperty(StyleId.STYLE_PROPERTY, StyleId.frameDecorated);

        // 设置标题
        mJFrame.setTitle("Asset Management System");

        // 注册面板 初始化语言
        UILanguageManager.registerComponent(mJFrame.getRootPane(), null);

        // // 透明度
        // mJFrame.setOpacity(0.83f);

        // 设置自定义框架图标
        final ImageIcon amsIcon = new ImageIcon(Frame.class.getResource("/ams/client/images/icons/frame/ams.png"));
        mJFrame.setIconImage(amsIcon.getImage());

        // // 总是显示在最顶层
        // mJFrame.setAlwaysOnTop(true);

        // 设置容器的尺寸
        mJFrame.setSize(1024, 576);

        // 设置顶级窗格的布局方式
        mJPanel.setLayout(new BorderLayout(15, 15));

        mWebDocumentPane.setOpaque(true);
        mJPanel.setOpaque(true);
        
        // 顶级窗格下添加默认文档窗格
        mJPanel.add(mWebDocumentPane);

        // 添加顶级窗格
        mJFrame.add(mJPanel);

        // 关闭窗体并退出程序
        mJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 使此窗口的大小适合其子组件的首选大小和布局
        // 这将覆盖 setSize()
        // mJFrame.pack();

        // 根据参数指定窗体位置
        // 让窗体居中显示
        mJFrame.setLocationRelativeTo(null);

        // 显示窗体
        mJFrame.setVisible(true);
    }

    public final JFrame getJFrame() {
        // 返回变量 JFrame
        return mJFrame;
    }

    public final JPanel getJPanel() {
        // 返回变量 JPanel
        return mJPanel;
    }

    public final WebDocumentPane<DocumentData<?>> getWebDocumentPane() {
        // 返回变量 WebDocumentPane
        return mWebDocumentPane;
    }
}