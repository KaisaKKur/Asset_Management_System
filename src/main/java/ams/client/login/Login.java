package ams.client.login;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.alee.api.resource.ClassResource;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.laf.window.WebFrame;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.language.LM;
import com.alee.managers.language.LanguageLocaleUpdater;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.language.data.Dictionary;
import com.alee.managers.style.StyleId;

import ams.client.App;
import ams.client.skin.light.LightSkin;

public class Login {
    public static char[] mPassword = { 'a', 'm', 's' };

    public static void login() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Install WebLaF as application L&F
                WebLookAndFeel.install(LightSkin.class);

                // 设置软件语言 默认 en
                LanguageManager.setLocale(App.getLocale());

                // 引入系统翻译文档库
                LanguageManager
                        .addDictionary(new Dictionary(new ClassResource(App.class, "language/cvr-language.xml")));

                // Registering listener to update current Locale according to language changes
                LanguageManager.addLanguageListener(new LanguageLocaleUpdater());

                // Creating decorated frame
                final WebFrame<?> frame = new WebFrame<>(LM.get("ams.application.windows.login.title"));
                // 设置自定义框架图标
                final ImageIcon amsIcon = new ImageIcon(Login.class.getResource("/ams/client/images/icons/frame/ams.png"));
                frame.setIconImage(amsIcon.getImage());
                frame.getRootPane().putClientProperty(StyleId.STYLE_PROPERTY, StyleId.frameDecorated);
                frame.setLayout(new FormLayout(10, 5));

                // 用户：管理员
                frame.add(new WebLabel(StyleId.label, LM.get("ams.application.windows.login.administrator")));
                final WebTextField WebTextFieldUsr = new WebTextField(15);
                WebTextFieldUsr.setEnabled(false);
                WebTextFieldUsr.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.textfield);
                WebTextFieldUsr.setText("admin");
                frame.add(WebTextFieldUsr);

                // 密码
                frame.add(new WebLabel(StyleId.label, LM.get("ams.application.windows.login.password")));
                final WebPasswordField WebPasswordFieldPassword = new WebPasswordField(15);
                WebPasswordFieldPassword.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.passwordfield);
                WebPasswordFieldPassword
                        .setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
                frame.add(WebPasswordFieldPassword);

                final WebPanel buttons = new WebPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                final ImageIcon enterIcon = new ImageIcon(Login.class.getResource("/ams/client/images/icons/menu/reboot.png"));
                final WebButton enter = new WebButton(StyleId.button, LM.get("ams.application.windows.login.enter"),
                        enterIcon);
                enter.addHotkey(Hotkey.ENTER);
                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final char[] password = WebPasswordFieldPassword.getPassword();

                        boolean flag = true;

                        if (password.length != mPassword.length) {
                            flag = false;
                        } else {
                            for (int i = 0; i < password.length; i++) {
                                if (password[i] != mPassword[i]) {
                                    flag = false;
                                }
                            }
                        }

                        if (flag) {
                            // 登录关闭窗口
                            frame.dispose();

                            // 委托线程启动 asm
                            App.boot();
                        }
                    }
                });

                final ImageIcon exitIcon = new ImageIcon(Login.class.getResource("/ams/client/images/icons/menu/exit.png"));
                final WebButton exit = new WebButton(StyleId.button, LM.get("ams.application.windows.login.exit"),
                        exitIcon);
                exit.addHotkey(Hotkey.ESCAPE);
                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });

                buttons.add(enter);
                buttons.add(exit);
                frame.add(buttons.equalizeComponentsWidth(), FormLayout.LINE);

                frame.pack();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}