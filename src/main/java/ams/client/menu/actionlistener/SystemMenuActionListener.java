package ams.client.menu.actionlistener;

import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.alee.api.annotations.NotNull;
import com.alee.api.annotations.Nullable;
import com.alee.extended.tab.DocumentData;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.TableCellArea;
import com.alee.laf.table.TableHeaderCellArea;
import com.alee.laf.table.TableHeaderToolTipProvider;
import com.alee.laf.table.TableToolTipProvider;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.icon.Icons;
import com.alee.managers.icon.LazyIcon;
import com.alee.managers.language.LM;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.alee.managers.style.StyleId;
import com.alee.utils.CoreSwingUtils;

import org.apache.commons.lang3.RandomStringUtils;

import ams.client.App;
import ams.client.menu.Common;
import ams.controller.data.table.TableDataController;

/**
 * 系统管理菜单活动监听器
 */
public class SystemMenuActionListener {
    /**
     * 退出系统
     * 
     * @return ActionListener for exit
     */
    public final ActionListener exit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

    public final ActionListener reboot() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 重启系统
                // 关闭当前窗体
                // 委托主线程启动系统
                App.reboot();
            }
        };
    }

    public final ActionListener categoryManagement() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取数据
                final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mAssetCategoryTable);
                // 创建资产类别表
                final WebTable table = new WebTable(StyleId.table, TableDataController.mAssetCategoryTable);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.optimizeColumnWidths(true);
                table.setOptimizeRowHeight(true);
                table.setPreferredScrollableViewportSize(new Dimension(500, 70));
                table.setFillsViewportHeight(true);
                table.setRowSorter(sorter);
                table.setHeaderToolTipProvider(new TableHeaderToolTipProvider<String>() {
                    /**
                     *
                     */
                    private static final long serialVersionUID = 1L;

                    @Nullable
                    @Override
                    protected String getToolTipText(@NotNull final JTableHeader tableHeader,
                            @NotNull final TableHeaderCellArea<String, JTableHeader> area) {
                        return LM.get("ams.application.windows.tables.header", area.column(),
                                area.getValue(tableHeader));
                    }
                });
                table.setToolTipProvider(new TableToolTipProvider<Object>() {
                    /**
                     *
                     */
                    private static final long serialVersionUID = 1L;

                    @Nullable
                    @Override
                    protected String getToolTipText(@NotNull final JTable table,
                            @NotNull final TableCellArea<Object, JTable> area) {
                        return LM.get("ams.application.windows.tables.cell", area.row(), area.column(),
                                area.getValue(table));
                    }
                });
                // 在 表格模型上 添加 数据改变监听器
                table.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        // 事件的类型，可能的值有:
                        // TableModelEvent.INSERT 新行或新列的添加
                        // TableModelEvent.UPDATE 现有数据的更改
                        // TableModelEvent.DELETE 有行或列被移除
                        int type = e.getType();

                        if (type == TableModelEvent.UPDATE || type == TableModelEvent.INSERT
                                || type == TableModelEvent.DELETE) {
                            // 配对类型
                            TableDataController.matchAssetType();
                            // 写入缓存数据
                            TableDataController.updateCache(TableDataController.getAssetCategoryTableData(),
                                    "AssetCategory.dat");

                            TableDataController.updateAssetTypeFormate();
                            AssetMenuActionListener.JComboBoxType
                                    .setModel(new DefaultComboBoxModel<>(TableDataController.mAssetTypeFormate));

                            if (type == TableModelEvent.UPDATE) {
                                int viewRow = table.getSelectedRow();
                                if (viewRow >= 0) {
                                    int row = table.convertRowIndexToModel(viewRow);
                                    String data;

                                    data = (String) TableDataController.mAssetCategoryTable.getValueAt(row, 0) + "-"
                                            + (String) TableDataController.mAssetCategoryTable.getValueAt(row, 1) + "-"
                                            + (String) TableDataController.mAssetCategoryTable.getValueAt(row, 2);

                                    for (int i = 0; i < TableDataController.mBorrowEmployTable.getRowCount(); i++) {
                                        if (TableDataController.mAssetCategoryTable.getValueAt(row, 0)
                                                .equals(TableDataController.mBorrowEmployTable.getValueAt(i, 2)
                                                        .toString().substring(0, 3))) {

                                            TableDataController.mBorrowEmployTable.setValueAt(data, i, 2);
                                        }
                                    }

                                    for (int i = 0; i < TableDataController.mRemandEmployTable.getRowCount(); i++) {
                                        if (TableDataController.mAssetCategoryTable.getValueAt(row, 0)
                                                .equals(TableDataController.mRemandEmployTable.getValueAt(i, 2)
                                                        .toString().substring(0, 3))) {

                                            TableDataController.mRemandEmployTable.setValueAt(data, i, 2);
                                        }
                                    }
                                }

                                TableDataController.updateCache(TableDataController.getEmployQueryTableData(),
                                        "AssetEmploy.dat");
                            }
                        }
                    }
                });

                // 将 table 添加至滚动窗格 并设置大小
                final WebScrollPane wScrollPane = new WebScrollPane(table).setPreferredSize(300, 100);
                // 创建根窗格
                final WebPanel rootWebPanel = new WebPanel();
                // 将滚动窗格添加至根窗格
                rootWebPanel.add(wScrollPane);

                // 创建子窗格 垂直布局
                final WebPanel childWebPanel = new WebPanel();
                childWebPanel.setLayout(new BoxLayout(childWebPanel, BoxLayout.Y_AXIS));
                // 将子窗格添加至根窗格
                rootWebPanel.add(childWebPanel, BorderLayout.SOUTH);

                /**
                 * 子盒一: childDownWebPanel_0
                 */
                final WebPanel childDownWebPanel_0 = new WebPanel();
                childDownWebPanel_0.setLayout(new BoxLayout(childDownWebPanel_0, BoxLayout.X_AXIS));
                // 添加至子窗格
                childWebPanel.add(childDownWebPanel_0);

                // 空白箱 分割占位组件
                childDownWebPanel_0.add(Box.createHorizontalStrut(100));

                // ID 水平布局
                final WebLabel webLabelId = new WebLabel(LM.get("ams.application.windows.tables.asset.column-name-id"));
                webLabelId.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldId = new WebTextField(StyleId.textfield, 20);
                webTextFieldId.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.search"));
                // textField.setTrailingComponent(new WebImage(DemoStyles.trailingImage,
                // DemoIcons.github16));
                webTextFieldId.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        Common.newFilter(table, webTextFieldId, sorter, 0);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        Common.newFilter(table, webTextFieldId, sorter, 0);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        Common.newFilter(table, webTextFieldId, sorter, 0);
                    }
                });

                webLabelId.setLabelFor(webTextFieldId);
                childDownWebPanel_0.add(webLabelId);
                childDownWebPanel_0.add(webTextFieldId);

                // 空白箱 分割占位组件
                childDownWebPanel_0.add(Box.createHorizontalStrut(15));

                // 资产大类
                final WebLabel webLabelBigCategory = new WebLabel(
                        LM.get("ams.application.windows.tables.asset.column-name-big-category"));
                webLabelBigCategory.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldBigCategory = new WebTextField(StyleId.textfield, 20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldBigCategory.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { table.getModel().getValueAt(row, 1) };
                            webTextFieldBigCategory.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldBigCategory);
                childDownWebPanel_0.add(webLabelBigCategory);
                childDownWebPanel_0.add(webTextFieldBigCategory);

                // 空白箱 分割占位组件
                childDownWebPanel_0.add(Box.createHorizontalStrut(15));

                // 资产小类
                final WebLabel webLabelSmallCategory = new WebLabel(
                        LM.get("ams.application.windows.tables.asset.column-name-small-category"));
                webLabelSmallCategory.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldSmallCategory = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldSmallCategory.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { table.getModel().getValueAt(row, 2) };
                            webTextFieldSmallCategory.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldSmallCategory);
                childDownWebPanel_0.add(webLabelSmallCategory);
                childDownWebPanel_0.add(webTextFieldSmallCategory);

                final BottonListener bottonListener = new BottonListener(table);

                // 修改
                // 加载图标
                final ImageIcon modifyIcon = new ImageIcon(SystemMenuActionListener.class.getResource("/ams/client/images/icons/menu/edit.png"));
                final JButton modify = new JButton(LM.get("ams.application.windows.button.modify"), modifyIcon);
                modify.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                childDownWebPanel_0.add(modify);
                modify.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Setup dialog decoration
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        // 添加通知会话
                        @NotNull
                        WebNotification<?> ntf = new WebNotification<>();

                        // 获取默认父组件
                        final Window parent = CoreSwingUtils.getNonNullWindowAncestor(modify);
                        final int viewRow = table.getSelectedRow();
                        int row = table.convertRowIndexToModel(viewRow);
                        final Object[] inputData = { webTextFieldBigCategory.getText().trim(),
                                webTextFieldSmallCategory.getText().trim() };

                        final String waringTitle = LM.get("ams.application.windows.dialog.title-waring");

                        if (inputData[0].equals("") || inputData[1].equals("") || table.getSelectedRows().length == 0) {
                            // 关闭会话
                            final String closed = LM.get("ams.application.windows.dialog.notification.close");
                            final String waringMessage = LM.get("ams.application.windows.dialog.operation-data");
                            // 创建按钮
                            final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
                            JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
                            ntf = NotificationManager.showNotification(parent, closed);
                        } else {
                            for (int i = 0, column = 1; i < inputData.length; i++, column++) {
                                TableDataController.mAssetCategoryTable.setValueAt(inputData[i], row, column);
                            }

                            ntf = NotificationManager.showNotification(parent,
                                    LM.get("ams.application.windows.dialog.notification.load"));
                        }

                        // 设置背景透明
                        ntf.setOpaque(false);
                        ntf.setDisplayTime(3000);
                    }
                });

                // 空白箱 分割占位组件
                childDownWebPanel_0.add(Box.createHorizontalStrut(100));

                /**
                 * 子盒二: boxEast
                 */
                final WebPanel boxEast = new WebPanel();
                boxEast.setLayout(new BoxLayout(boxEast, BoxLayout.Y_AXIS));

                // 空白箱 分割占位组件 使 box 下往上布局
                boxEast.add(Box.createHorizontalStrut(0));

                // 添加
                // 加载图标
                final ImageIcon addIcon = new ImageIcon(SystemMenuActionListener.class.getResource("/ams/client/images/icons/menu/new.png"));
                final JButton add = new JButton(LM.get("ams.application.windows.button.add"), addIcon);
                add.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                boxEast.add(add);
                add.setActionCommand("add");
                add.addActionListener(bottonListener);

                // 删除
                // 加载图标
                final ImageIcon deleteIcon = new ImageIcon(SystemMenuActionListener.class.getResource("/ams/client/images/icons/menu/exit.png"));
                final JButton delete = new JButton(LM.get("ams.application.windows.button.delete"), deleteIcon);
                delete.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                boxEast.add(delete);
                delete.setActionCommand("delete");
                delete.addActionListener(bottonListener);

                // 添加至根窗格
                rootWebPanel.add(boxEast, BorderLayout.EAST);

                // 加载 title 图标
                final LazyIcon icon = Icons.computer;
                // 本地化表名
                final String title = LM.get("ams.application.windows.tables.title-name",
                        LM.get("ams.application.windows.tables.asset.name"));
                        
                // 打开文档
                App.getWebDocumentPane()
                        .openDocument(new DocumentData<>(title, icon, title, Color.WHITE, rootWebPanel));
            }
        };
    }

    private class BottonListener implements ActionListener {
        
        private WebTable mTable = null;
        // 获取默认父组件
        private final Window mParent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());

        BottonListener(WebTable table) {
            mTable = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Setup dialog decoration
            JDialog.setDefaultLookAndFeelDecorated(true);
            // 添加通知会话
            @NotNull
            WebNotification<?> ntf = new WebNotification<>();

            final String buttonName = e.getActionCommand();

            if (buttonName.equals("add")) {
                // Display option pane
                final String title = LM.get("ams.application.windows.dialog.title.add-data");
                // 确定会话
                final String save = LM.get("ams.application.windows.dialog.notification.save");
                // 关闭会话
                final String closed = LM.get("ams.application.windows.dialog.notification.close");

                final WebTextField webTextFieldIdCategory = new WebTextField(StyleId.textfield, 20);
                webTextFieldIdCategory.setEditable(false);
                webTextFieldIdCategory.setForeground(Color.BLUE);

                // 生成随机独立 id
                String randomId = RandomStringUtils.randomAlphanumeric(3);

                for (int i = 0; i < TableDataController.mAssetCategoryTable.getRowCount(); i++) {
                    if (TableDataController.mAssetCategoryTable.getValueAt(i, 0).equals(randomId)) {
                        randomId = RandomStringUtils.randomAlphanumeric(3);
                        i = 0;
                    }
                }

                webTextFieldIdCategory.setText(randomId);

                final WebTextField webTextFieldBigCategory = new WebTextField(StyleId.textfield, 20);
                webTextFieldBigCategory
                        .setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
                final WebTextField webTextFieldSmallCategory = new WebTextField(StyleId.textfield, 20);
                webTextFieldSmallCategory
                        .setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));

                // 创建按钮
                final Object[] options = { LM.get("ams.application.windows.dialog.save"),
                        LM.get("ams.application.windows.dialog.cancel") };

                final Object[] message = { LM.get("ams.application.windows.tables.asset.column-name-id"),
                        webTextFieldIdCategory, LM.get("ams.application.windows.tables.asset.column-name-big-category"),
                        webTextFieldBigCategory,
                        LM.get("ams.application.windows.tables.asset.column-name-small-category"),
                        webTextFieldSmallCategory };

                final int option = JOptionPane.showOptionDialog(mParent, message, title, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                // 获取文本框数据 去掉尾部空格
                String[] inputData = new String[] { webTextFieldIdCategory.getText().trim(),
                        webTextFieldBigCategory.getText().trim(), webTextFieldSmallCategory.getText().trim() };

                if (option == JOptionPane.OK_OPTION) {
                    if (inputData[1].equals("") || inputData[2].equals("")) {
                        final String waringTitle = LM.get("ams.application.windows.dialog.title-waring");
                        final String waringMessage = LM.get("ams.application.windows.dialog.waring-data");
                        // 创建按钮
                        final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
                        JOptionPane.showOptionDialog(mParent, waringMessage, waringTitle, JOptionPane.YES_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
                        ntf = NotificationManager.showNotification(mParent, closed);
                    } else {
                        TableDataController.mAssetCategoryTable.addRow(inputData);
                        ntf = NotificationManager.showNotification(mParent, save);
                    }
                } else {
                    ntf = NotificationManager.showNotification(mParent, closed);
                }
            } else if (buttonName.equals("delete")) {
                final String waringTitle = LM.get("ams.application.windows.dialog.title.delete-data");
                final String waringMessage;
                // 创建按钮
                Object[] waringOption;

                if (mTable.getSelectedRows().length != 0) {
                    waringMessage = LM.get("ams.application.windows.dialog.delete-data");
                    waringOption = new Object[2];
                    waringOption[0] = LM.get("ams.application.windows.dialog.confirm");
                    waringOption[1] = LM.get("ams.application.windows.dialog.cancel");

                    int viewRow = mTable.getSelectedRow();
                    int row = -1;
                    if (viewRow >= 0) {
                        row = mTable.convertRowIndexToModel(viewRow);
                    }

                    boolean flag = true;

                    for (int i = 0; i < TableDataController.mAssetInfoTable.getRowCount(); i++) {
                        if (TableDataController.mAssetCategoryTable.getValueAt(row, 0).equals(
                                TableDataController.mAssetInfoTable.getValueAt(i, 2).toString().substring(0, 3))) {
                            flag = false;
                            break;
                        }
                    }

                    if (!flag) {
                        String message = LM.get("ams.application.windows.dialog.category-conflict-data");
                        // 创建按钮
                        Object[] options = { LM.get("ams.application.windows.dialog.confirm") };
                        JOptionPane.showOptionDialog(mParent, message, waringTitle, JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    } else {
                        int option = JOptionPane.showOptionDialog(mParent, waringMessage, waringTitle,
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, waringOption,
                                waringOption[0]);

                        if (option == JOptionPane.YES_OPTION && mTable.getSelectedRows().length != 0) {
                            // 删除选中的行
                            TableDataController.mAssetCategoryTable.removeRow(row);

                            ntf = NotificationManager.showNotification(mParent,
                                    LM.get("ams.application.windows.dialog.notification.delete"));
                        } else {
                            ntf = NotificationManager.showNotification(mParent,
                                    LM.get("ams.application.windows.dialog.notification.close"));
                        }
                    }
                } else {
                    waringMessage = LM.get("ams.application.windows.dialog.no-select-data");
                    waringOption = new Object[1];
                    waringOption[0] = LM.get("ams.application.windows.dialog.confirm");

                    JOptionPane.showOptionDialog(mParent, waringMessage, waringTitle, JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);

                }
            }

            // Restore dialog decoration if needed
            JDialog.setDefaultLookAndFeelDecorated(false);

            // 设置背景透明
            ntf.setOpaque(false);
            ntf.setDisplayTime(3000);
        }
    }

}