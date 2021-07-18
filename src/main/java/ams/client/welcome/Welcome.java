package ams.client.welcome;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.alee.extended.tab.DocumentData;
import com.alee.managers.icon.Icons;
import com.alee.managers.icon.LazyIcon;
import com.alee.managers.language.LM;

import ams.client.App;

public class Welcome {
    public Welcome() {
        final BackgroundPanel background = new BackgroundPanel(
                (new ImageIcon(Welcome.class.getResource("/ams/client/images/welcome/welcome.jpg")).getImage()));
        background.setBounds(0, 0, 400, 300);

        // 加载 title 图标
        final LazyIcon icon = Icons.computer;
        // 本地化表名
        final String title = LM.get("ams.application.windows.tables.title-name",
                LM.get("ams.application.windows.tables.welcome"));

        // 打开文档
        App.getWebDocumentPane().openDocument(new DocumentData<>(title, icon, title, Color.WHITE, background));
    }

    class BackgroundPanel extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        Image im;

        public BackgroundPanel(Image im) {
            this.im = im;
            this.setOpaque(true);
        }

        // Draw the back ground.
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);

        }
    }

}