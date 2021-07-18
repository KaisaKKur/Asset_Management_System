package ams.client.menu.assetemploy;

import com.alee.managers.hotkey.Hotkey;
import com.alee.utils.swing.menu.JMenuBarGenerator;

import ams.client.menu.Menu;
import ams.client.menu.actionlistener.AssetEmployMenuActionListener;

public class AssetEmployMenu extends Menu {
        // 创建资产服务管理菜单活动监听器
        private static final AssetEmployMenuActionListener mActionListener = new AssetEmployMenuActionListener();

        public AssetEmployMenu(JMenuBarGenerator menuBarGTR) {
                super(menuBarGTR, "asset-employ-management", "asset-employ-management");

                // 资产领用管理
                mMenuGTR.addItem("asset-employ-borrow", "asset-employ-borrow", Hotkey.ALT_B, mActionListener.borrowEmploy());

                // 分割线
                mMenuGTR.addSeparator();

                // 资产归还管理
                mMenuGTR.addItem("asset-employ-remand", "asset-employ-remand", Hotkey.ALT_R, mActionListener.remandEmploy());

                // 分割线
                mMenuGTR.addSeparator();

                // 资产报废管理
                mMenuGTR.addItem("asset-employ-scrapped", "asset-employ-scrapped", Hotkey.ALT_S, mActionListener.scrappedEmpoly());

                // 分割线
                mMenuGTR.addSeparator();

                // 资产报废管理
                mMenuGTR.addItem("asset-employ-query", "asset-employ-query", Hotkey.ALT_Q, mActionListener.queryEmpoly());
        }

}