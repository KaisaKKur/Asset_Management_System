package ams.client.menu;

import com.alee.api.annotations.Nullable;
import com.alee.utils.swing.menu.JMenuBarGenerator;
import com.alee.utils.swing.menu.MenuGenerator;

abstract public class Menu {

    protected MenuGenerator mMenuGTR;

    protected Menu(JMenuBarGenerator menuBarGTR, @Nullable Object icon, @Nullable String text) {
        mMenuGTR = menuBarGTR.addSubMenu(icon, text);
    }

    /**
     * 
     * @return a menu gtr
     */
    public MenuGenerator getMenuGTR() {
        return mMenuGTR;
    }
}