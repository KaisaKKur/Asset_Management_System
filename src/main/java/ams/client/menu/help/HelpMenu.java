package ams.client.menu.help;

import com.alee.managers.hotkey.Hotkey;
import com.alee.utils.swing.menu.JMenuBarGenerator;
import com.alee.utils.swing.menu.MenuGenerator;

import ams.client.menu.Menu;
import ams.client.menu.actionlistener.HelpMenuActionListener;

public class HelpMenu extends Menu {
    // 创建资产服务管理菜单活动监听器
    private static final HelpMenuActionListener mActionListener = new HelpMenuActionListener();

    public HelpMenu(JMenuBarGenerator menuBarGTR) {
        super(menuBarGTR, "help", "help");

        // 添加子菜单项
        final MenuGenerator subMenuLanguage = mMenuGTR.addSubMenu("languagechooser", "languagechooser");
        subMenuLanguage.addItem("zh", "chinses", Hotkey.CTRL_Z, mActionListener.languageChooserZh());
        subMenuLanguage.addItem("en", "english", Hotkey.CTRL_E, mActionListener.languageChooserEn());

        // 分割线
        mMenuGTR.addSeparator();

        mMenuGTR.addItem("update", "update", Hotkey.CTRL_U, mActionListener.showUpdateDialog());

        // 分割线
        mMenuGTR.addSeparator();

        // 添加子菜单项
        mMenuGTR.addItem("about", "about", Hotkey.CTRL_A, mActionListener.showAboutDialog());

    }

}