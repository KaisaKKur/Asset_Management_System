package ams.client.controller;

import ams.client.frame.Frame;
import ams.client.menu.MenuBar;

public class Controller {
    // 创建窗体
    private final Frame mFrame = new Frame();

    public Controller() {
        // 设置主界面
        setInterface();
    }

    /**
     * 主界面
     */
    private final void setInterface() {
        // 统一设置字体
        // AWTFont.InitGlobalFont(new Font("微软雅黑", Font.BOLD, 12));

        // 创建菜单栏控制器
        MenuBar menuBar = new MenuBar();

        // 添加菜单栏
        mFrame.getJFrame().setJMenuBar(menuBar.getMenuBar());
    }

    public Frame getFrame() {
        return mFrame;
    }

    // 重新布置窗体
    public void dispose() {
        mFrame.getJFrame().dispose();
	}
}