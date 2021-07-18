package ams.client.menu.system;

import com.alee.managers.hotkey.Hotkey;
import com.alee.utils.swing.menu.JMenuBarGenerator;

import ams.client.menu.Menu;
import ams.client.menu.actionlistener.AssetMenuActionListener;
import ams.client.menu.actionlistener.StaffMenuActionListener;
import ams.client.menu.actionlistener.SystemMenuActionListener;

public class SystemMenu extends Menu {
    // 创建系统管理菜单活动监听器
    private static final SystemMenuActionListener mSystemActionListener = new SystemMenuActionListener();
    // 创建资产信息管理菜单活动监听器
    private static final AssetMenuActionListener mAssetActionListener = new AssetMenuActionListener();
    // 创建人员信息管理菜单活动监听器
    private static final StaffMenuActionListener mStaffActionListener = new StaffMenuActionListener();

    public SystemMenu(JMenuBarGenerator menuBarGTR) {
        // 初始化超类
        super(menuBarGTR, "system-management", "system-management");

        // 创建子菜单项
        mMenuGTR.addItem("category", "category", Hotkey.CTRL_C, mSystemActionListener.categoryManagement());
        mMenuGTR.addItem("asset-management", "asset-management", Hotkey.CTRL_A, mAssetActionListener.assetInfoManagement());
        mMenuGTR.addItem("staff-management", "staff-management", Hotkey.CTRL_A, mStaffActionListener.staffInfoManagement());
        
        // 分割线
        mMenuGTR.addSeparator();
        
        mMenuGTR.addItem("reboot", "reboot", Hotkey.CTRL_R, mSystemActionListener.reboot());
        mMenuGTR.addItem("exit", "exit", Hotkey.ESCAPE, mSystemActionListener.exit());
    }
}