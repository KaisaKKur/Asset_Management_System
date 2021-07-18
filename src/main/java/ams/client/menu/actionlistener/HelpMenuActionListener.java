package ams.client.menu.actionlistener;

import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.alee.api.annotations.NotNull;
import com.alee.managers.language.LM;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.alee.utils.CoreSwingUtils;

import ams.client.App;

public class HelpMenuActionListener {

    public final ActionListener showAboutDialog() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Setup dialog decoration
                JDialog.setDefaultLookAndFeelDecorated(true);

                String[] message = { "version: 1.0", "Studio: Cipher Studio", "Group: cipherpowered", "Author: Cipher",
                        "Business Type: open", "OS: Windows x64 10.0.18363" };
                // 加载信息
                String versionMessage = "";

                for (int i = 0; i < message.length; i++) {
                    versionMessage += (message[i] + "\n");
                }

                // Display option pane
                // 获取默认父组件
                final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
                final String title = LM.get("ams.application.windows.dialog.title-ams");

                // 通知会话
                final String notification = LM.get("ams.application.windows.dialog.notification");
                // 确定会话
                final String confirm = LM.get("ams.application.windows.dialog.confirm");
                // 关闭会话
                final String closed = LM.get("ams.application.windows.dialog.notification.about.closed");
                // 复制会话
                final String[] copy = { LM.get("ams.application.windows.dialog.copy"),
                        LM.get("ams.application.windows.dialog.notification.about.copy") };

                // 添加通知会话
                @NotNull
                WebNotification<?> ntf = new WebNotification<>();

                // 创建按钮
                final Object[] options = { LM.get("ams.application.windows.dialog.confirm"),
                        LM.get("ams.application.windows.dialog.copy") };

                // 创建对话框
                final int option = JOptionPane.showOptionDialog(parent, versionMessage, title,
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                // 操作活动
                if (option == JOptionPane.NO_OPTION) {
                    // 获取系统剪贴板
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    // 封装文本内容
                    Transferable trans = new StringSelection(versionMessage);
                    // 把文本内容设置到系统剪贴板
                    clipboard.setContents(trans, null);

                    ntf = NotificationManager.showNotification(parent,
                            String.format(notification + "%s\n%s", copy[0], copy[1]));
                } else if (option == JOptionPane.YES_OPTION) {
                    ntf = NotificationManager.showNotification(parent,
                            String.format(notification + "%s\n%s", confirm, closed));
                } else {
                    ntf = NotificationManager.showNotification(parent, closed);
                }

                // Restore dialog decoration if needed
                JDialog.setDefaultLookAndFeelDecorated(false);

                // 设置背景透明
                ntf.setOpaque(false);
                ntf.setDisplayTime(3000);
            }
        };
    }

    public ActionListener languageChooserZh() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 设置软件语言
                LanguageManager.setLocale(App.getLocale());
                // 关闭文档刷新
                App.getWebDocumentPane().closeAll();
            }
        };
    }

    public ActionListener languageChooserEn() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 设置软件语言
                LanguageManager.setLocale(Locale.US);
                // 关闭文档刷新
                App.getWebDocumentPane().closeAll();
            }
        };
    }

    public ActionListener showUpdateDialog() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Setup dialog decoration
                JDialog.setDefaultLookAndFeelDecorated(true);

                // Display option pane
                // 获取默认父组件
                final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
                final String title = LM.get("ams.application.windows.dialog.title-ams");

                // 关闭会话
                final String closed = LM.get("ams.application.windows.dialog.notification.close");

                // 添加通知会话
                @NotNull
                WebNotification<?> ntf = new WebNotification<>();

                // 创建按钮
                final Object[] options = { LM.get("ams.application.windows.dialog.confirm") };

                Object message = LM.get("ams.application.windows.dialog.update");
                // 创建对话框
                JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                ntf = NotificationManager.showNotification(parent, closed);

                // Restore dialog decoration if needed
                JDialog.setDefaultLookAndFeelDecorated(false);

                // 设置背景透明
                ntf.setOpaque(false);
                ntf.setDisplayTime(3000);
            }
        };
    }
}