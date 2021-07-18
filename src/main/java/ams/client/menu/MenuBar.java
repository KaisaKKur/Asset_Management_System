package ams.client.menu;

import com.alee.managers.style.StyleId;
import com.alee.utils.swing.menu.JMenuBarGenerator;

import ams.client.menu.assetemploy.AssetEmployMenu;
import ams.client.menu.help.HelpMenu;
import ams.client.menu.system.SystemMenu;

import javax.swing.*;

public class MenuBar {

    // Creating new menu bar
    final JMenuBar mJMenuBar = new JMenuBar();

    // Filling menu bar with items
    final JMenuBarGenerator mMenuBarGTR = new JMenuBarGenerator(mJMenuBar);

    public MenuBar() {
        // 初始化菜单栏
        initialMenuBar();

        // 设置菜单栏
        setMenuBar();
    }

    private void initialMenuBar() {
        // 引用样式
        mJMenuBar.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.menubar);

        // 设置语言库
        mMenuBarGTR.setLanguagePrefix("ams.application.windows.menus.menu");
        // 设置菜单图标
        mMenuBarGTR.setIconSettings(MenuBar.class, "../images/icons/menu/", "png");
    }

    private void setMenuBar() {
        // 系统管理菜单
        SystemMenu systemMenu = new SystemMenu(mMenuBarGTR); // | 传递菜单栏生成器
        mJMenuBar.add(systemMenu.getMenuGTR().getMenu());

        // 资产服务管理
        AssetEmployMenu assetEmployMenu = new AssetEmployMenu(mMenuBarGTR);
        mJMenuBar.add(assetEmployMenu.getMenuGTR().getMenu());

        // 帮助菜单
        HelpMenu helpMenu = new HelpMenu(mMenuBarGTR);
        mJMenuBar.add(helpMenu.getMenuGTR().getMenu());
    }

    /**
     * Returns new menu bar.
     *
     * @return new menu bar
     */
    public JMenuBar getMenuBar() {
        return mJMenuBar;
    }
}