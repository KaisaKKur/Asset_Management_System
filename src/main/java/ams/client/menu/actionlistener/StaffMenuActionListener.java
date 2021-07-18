package ams.client.menu.actionlistener;

import java.awt.Window;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
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
import com.alee.laf.text.WebTextArea;
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

public class StaffMenuActionListener {
    public ActionListener staffInfoManagement() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取数据
                final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mStaffInfoTable);
                // 创建人员信息表
                final WebTable table = new WebTable(StyleId.table, TableDataController.mStaffInfoTable);
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
                            // 写入缓存数据
                            TableDataController.updateCache(TableDataController.getStaffInfoTableData(),
                                    "StaffInfo.dat");

                            TableDataController.updateStaffFormate();
                            AssetEmployMenuActionListener.JComboBoxStaffBorrow
                                    .setModel(new DefaultComboBoxModel<>(TableDataController.mStaffFormate));
                            AssetEmployMenuActionListener.JComboBoxStaffScrap
                                    .setModel(new DefaultComboBoxModel<>(TableDataController.mStaffFormate));

                            if (type == TableModelEvent.UPDATE) {
                                int viewRow = table.getSelectedRow();
                                if (viewRow >= 0) {
                                    int row = table.convertRowIndexToModel(viewRow);
                                    String data;

                                    data = (String) TableDataController.mStaffInfoTable.getValueAt(row, 0) + "-"
                                            + (String) TableDataController.mStaffInfoTable.getValueAt(row, 1) + "-"
                                            + (String) TableDataController.mStaffInfoTable.getValueAt(row, 3);

                                    for (int i = 0; i < TableDataController.mEmployQueryTable.getRowCount(); i++) {
                                        if (TableDataController.mStaffInfoTable.getValueAt(row, 0)
                                                .equals(TableDataController.mEmployQueryTable.getValueAt(i, 3)
                                                        .toString().substring(0, 4))) {

                                            TableDataController.mEmployQueryTable.setValueAt(data, i, 3);
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
                final WebLabel webLabelId = new WebLabel(
                        LM.get("ams.application.windows.tables.staff-info.column-name-id"));
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
                childDownWebPanel_0.add(Box.createHorizontalStrut(700));

                /**
                 * 子盒二: childDownWebPanel_1
                 */
                final WebPanel childDownWebPanel_1 = new WebPanel();
                childDownWebPanel_1.setLayout(new BoxLayout(childDownWebPanel_1, BoxLayout.X_AXIS));
                // 添加至子窗格
                childWebPanel.add(childDownWebPanel_1);

                // 空白箱 分割占位组件
                childDownWebPanel_1.add(Box.createHorizontalStrut(100));

                // 姓名
                final WebLabel webLabelName = new WebLabel(
                        LM.get("ams.application.windows.tables.staff-info.column-name"));
                webLabelName.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldName = new WebTextField(StyleId.textfield, 20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldName.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mStaffInfoTable.getValueAt(row, 1) };
                            webTextFieldName.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldName);
                childDownWebPanel_1.add(webLabelName);
                childDownWebPanel_1.add(webTextFieldName);

                // 空白箱 分割占位组件
                childDownWebPanel_1.add(Box.createHorizontalStrut(15));

                // 性别
                final WebLabel webLabelSex = new WebLabel(
                        LM.get("ams.application.windows.tables.staff-info.column-name-sex"));
                webLabelSex.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldSex = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldSex.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mStaffInfoTable.getValueAt(row, 2) };
                            webTextFieldSex.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldSex);
                childDownWebPanel_1.add(webLabelSex);
                childDownWebPanel_1.add(webTextFieldSex);

                // 空白箱 分割占位组件
                childDownWebPanel_1.add(Box.createHorizontalStrut(15));

                // 部门
                final WebLabel webLabelDept = new WebLabel(
                        LM.get("ams.application.windows.tables.staff-info.column-name-dept"));
                webLabelDept.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldDept = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldDept.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mStaffInfoTable.getValueAt(row, 3) };
                            webTextFieldDept.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldDept);
                childDownWebPanel_1.add(webLabelDept);
                childDownWebPanel_1.add(webTextFieldDept);

                // 空白箱 分割占位组件
                childDownWebPanel_1.add(Box.createHorizontalStrut(100));

                /**
                 * 子盒三: childDownWebPanel_2
                 */
                final WebPanel childDownWebPanel_2 = new WebPanel();
                childDownWebPanel_2.setLayout(new BoxLayout(childDownWebPanel_2, BoxLayout.X_AXIS));
                // 添加至子窗格
                childWebPanel.add(childDownWebPanel_2);

                // 空白箱 分割占位组件
                childDownWebPanel_2.add(Box.createHorizontalStrut(100));

                // 职位
                final WebLabel webLabelJob = new WebLabel(
                        LM.get("ams.application.windows.tables.staff-info.column-name-job"));
                webLabelJob.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldJob = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldJob.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mStaffInfoTable.getValueAt(row, 4) };
                            webTextFieldJob.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldJob);
                childDownWebPanel_2.add(webLabelJob);
                childDownWebPanel_2.add(webTextFieldJob);

                // 空白箱 分割占位组件
                childDownWebPanel_2.add(Box.createHorizontalStrut(15));

                // 其它
                final WebLabel webLabelOther = new WebLabel(
                        LM.get("ams.application.windows.tables.staff-info.column-name-other"));
                webLabelOther.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldOther = new WebTextField(50);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldOther.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mStaffInfoTable.getValueAt(row, 5) };
                            webTextFieldOther.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldOther);
                childDownWebPanel_2.add(webLabelOther);
                childDownWebPanel_2.add(webTextFieldOther);

                // 空白箱 分割占位组件
                childDownWebPanel_2.add(Box.createHorizontalStrut(15));

                // 修改
                // 加载图标
                final ImageIcon modifyIcon = new ImageIcon(StaffMenuActionListener.class.getResource("/ams/client/images/icons/menu/edit.png"));
                final JButton modify = new JButton(LM.get("ams.application.windows.button.modify"), modifyIcon);
                modify.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                childDownWebPanel_2.add(modify);
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
                        final Object[] inputData = { webTextFieldName.getText().trim(),
                                webTextFieldSex.getText().trim(), webTextFieldDept.getText().trim(),
                                webTextFieldJob.getText().trim(), webTextFieldOther.getText().trim() };

                        final String waringTitle = LM.get("ams.application.windows.dialog.title.modify-data");

                        if (inputData[0].equals("") || inputData[1].equals("") || inputData[2].equals("")
                                || table.getSelectedRows().length == 0) {
                            // 关闭会话
                            final String closed = LM.get("ams.application.windows.dialog.notification.close");
                            final String waringMessage = LM.get("ams.application.windows.dialog.operation-data");
                            // 创建按钮
                            final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
                            JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
                            ntf = NotificationManager.showNotification(parent, closed);
                        } else {
                            // 修改表
                            for (int i = 0, column = 1; i < inputData.length; i++, column++) {
                                TableDataController.mStaffInfoTable.setValueAt(inputData[i], row, column);
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
                childDownWebPanel_2.add(Box.createHorizontalStrut(100));

                /**
                 * 子盒: boxEast
                 */
                final WebPanel boxEast = new WebPanel();
                boxEast.setLayout(new BoxLayout(boxEast, BoxLayout.Y_AXIS));

                // 空白箱 分割占位组件 使 box 下往上布局
                boxEast.add(Box.createHorizontalStrut(0));

                final BottonListener bottonListener = new BottonListener(table);

                // 添加
                // 加载图标
                final ImageIcon addIcon = new ImageIcon(StaffMenuActionListener.class.getResource("/ams/client/images/icons/menu/new.png"));
                final JButton add = new JButton(LM.get("ams.application.windows.button.add"), addIcon);
                add.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                boxEast.add(add);
                add.setActionCommand("add");
                add.addActionListener(bottonListener);

                // 删除
                // 加载图标
                final ImageIcon deleteIcon = new ImageIcon(StaffMenuActionListener.class.getResource("/ams/client/images/icons/menu/exit.png"));
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
                        LM.get("ams.application.windows.tables.staff-info.name"));
                // 打开文档
                App.getWebDocumentPane()
                        .openDocument(new DocumentData<>(title, icon, title, Color.WHITE, rootWebPanel));
            }
        };
    }

    private class BottonListener implements ActionListener {
        private WebTable mTable = null;

        BottonListener(WebTable table) {
            mTable = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final String buttonName = e.getActionCommand();

            if (buttonName.equals("add")) {
                addAssetInfo();
            } else if (buttonName.equals("delete")) {
                deleteAssetInfo(mTable);
            }
        }
    }

    public void addAssetInfo() {
        // 获取默认父组件
        final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
        // Setup dialog decoration
        JDialog.setDefaultLookAndFeelDecorated(true);
        // 添加通知会话
        @NotNull
        WebNotification<?> ntf = new WebNotification<>();

        // Display option pane
        final String title = LM.get("ams.application.windows.dialog.title.add-data");
        // 确定会话
        final String save = LM.get("ams.application.windows.dialog.notification.save");
        // 关闭会话
        final String closed = LM.get("ams.application.windows.dialog.notification.close");

        // 创建按钮
        final Object[] options = { LM.get("ams.application.windows.dialog.save"),
                LM.get("ams.application.windows.dialog.cancel") };

        // 内容
        final WebPanel[] webPanelAll = new WebPanel[8];
        // ID
        final WebLabel webLabelId = new WebLabel(LM.get("ams.application.windows.tables.staff-info.column-name-id"));
        webLabelId.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
        final WebTextField webTextFieldId = new WebTextField(StyleId.textfield, 20);
        webTextFieldId.setEditable(false);
        webTextFieldId.setForeground(Color.BLUE);

        // 生成随机独立 id
        String randomId = RandomStringUtils.randomAlphanumeric(4);

        for (int i = 0; i < TableDataController.mAssetInfoTable.getRowCount(); i++) {
            if (TableDataController.mAssetInfoTable.getValueAt(i, 0).equals(randomId)) {
                randomId = RandomStringUtils.randomAlphanumeric(4);
                i = 0;
            }
        }

        webTextFieldId.setText(randomId);

        webPanelAll[0] = new WebPanel(StyleId.panelDecorated);
        webPanelAll[0].add(webLabelId, BorderLayout.WEST);
        webPanelAll[0].add(webTextFieldId, BorderLayout.EAST);

        // 姓名
        final WebLabel webLabelName = new WebLabel(LM.get("ams.application.windows.tables.staff-info.column-name"));
        webLabelName.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
        final WebTextField webTextFieldName = new WebTextField(StyleId.textfield, 20);
        webTextFieldName.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
        webPanelAll[1] = new WebPanel(StyleId.panelDecorated);
        webPanelAll[1].add(webLabelName, BorderLayout.WEST);
        webPanelAll[1].add(webTextFieldName, BorderLayout.EAST);

        // 性别
        final WebLabel webLabelSex = new WebLabel(LM.get("ams.application.windows.tables.staff-info.column-name-sex"));
        webLabelSex.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
        final WebTextField webTextFieldSex = new WebTextField(StyleId.textfield, 20);
        webTextFieldSex.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
        webPanelAll[2] = new WebPanel(StyleId.panelDecorated);
        webPanelAll[2].add(webLabelSex, BorderLayout.WEST);
        webPanelAll[2].add(webTextFieldSex, BorderLayout.EAST);

        // 部门
        final WebLabel webLabelDept = new WebLabel(
                LM.get("ams.application.windows.tables.staff-info.column-name-dept"));
        webLabelDept.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
        final WebTextField webTextFieldDept = new WebTextField(StyleId.textfield, 20);
        webTextFieldDept.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
        webPanelAll[3] = new WebPanel(StyleId.panelDecorated);
        webPanelAll[3].add(webLabelDept, BorderLayout.WEST);
        webPanelAll[3].add(webTextFieldDept, BorderLayout.EAST);

        // 职位
        final WebLabel webLabelJob = new WebLabel(LM.get("ams.application.windows.tables.staff-info.column-name-job"));
        webLabelJob.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
        final WebTextField webTextFieldJob = new WebTextField(StyleId.textfield, 20);
        webTextFieldJob.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
        webPanelAll[4] = new WebPanel(StyleId.panelDecorated);
        webPanelAll[4].add(webLabelJob, BorderLayout.WEST);
        webPanelAll[4].add(webTextFieldJob, BorderLayout.EAST);

        // 备注
        final WebLabel webLabelOther = new WebLabel(
                LM.get("ams.application.windows.tables.staff-info.column-name-other"));
        final WebTextArea webTextAreaOther = new WebTextArea(StyleId.textareaDecorated, 3, 20);
        webTextAreaOther.setLineWrap(true);
        webTextAreaOther.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
        webPanelAll[5] = new WebPanel(StyleId.panelDecorated);
        webPanelAll[5].add(webLabelOther, BorderLayout.WEST);
        webPanelAll[5].add(webTextAreaOther, BorderLayout.EAST);

        final Object[] message = { webPanelAll[0], webPanelAll[1], webPanelAll[2], webPanelAll[3], webPanelAll[4],
                webPanelAll[5] };

        final int option = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        // 获取文本框数据 去掉尾部空格
        String[] inputData = new String[] { webTextFieldId.getText().trim(), webTextFieldName.getText().trim(),
                webTextFieldSex.getText().toString(), webTextFieldDept.getText().trim(),
                webTextFieldJob.getText().trim(), webTextAreaOther.getText().trim() };

        if (option == JOptionPane.OK_OPTION) {
            if (inputData[1].equals("") || inputData[2].equals("") || inputData[3].equals("")) {
                final String waringTitle = LM.get("ams.application.windows.dialog.title-waring");
                final String waringMessage = LM.get("ams.application.windows.dialog.waring-data");
                // 创建按钮
                final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
                JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
                ntf = NotificationManager.showNotification(parent, closed);
            } else {
                TableDataController.mStaffInfoTable.addRow(inputData);
                ntf = NotificationManager.showNotification(parent, save);
            }
        } else {
            ntf = NotificationManager.showNotification(parent, closed);
        }

        // Restore dialog decoration if needed
        JDialog.setDefaultLookAndFeelDecorated(false);

        // 设置背景透明
        ntf.setOpaque(false);
        ntf.setDisplayTime(3000);
    }

    public void deleteAssetInfo(WebTable table) {
        // 获取默认父组件
        final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
        // Setup dialog decoration
        JDialog.setDefaultLookAndFeelDecorated(true);
        // 添加通知会话
        @NotNull
        WebNotification<?> ntf = new WebNotification<>();

        final String waringTitle = LM.get("ams.application.windows.dialog.title.delete-data");
        final String waringMessage;
        // 创建按钮
        Object[] waringOption;

        if (table.getSelectedRows().length != 0) {
            waringMessage = LM.get("ams.application.windows.dialog.delete-data");
            waringOption = new Object[2];
            waringOption[0] = LM.get("ams.application.windows.dialog.confirm");
            waringOption[1] = LM.get("ams.application.windows.dialog.cancel");

            int viewRow = table.getSelectedRow();
            int row = -1;
            if (viewRow >= 0) {
                row = table.convertRowIndexToModel(viewRow);
            }

            boolean flag = true;

            for (int i = TableDataController.mEmployQueryTable.getRowCount() - 1; i >= 0; i--) {
                if (TableDataController.mStaffInfoTable.getValueAt(row, 0)
                        .equals(TableDataController.mEmployQueryTable.getValueAt(i, 3).toString().substring(0, 4))) {
                    if (TableDataController.mEmployQueryTable.getValueAt(i, 1).equals("领用")) {
                        final String string = (String) TableDataController.mEmployQueryTable.getValueAt(i, 0);
                        
                        for (int j = 0; j < TableDataController.mAssetInfoTable.getRowCount(); j++) {
                            if (TableDataController.mAssetInfoTable.getValueAt(j, 0).equals(string)) {
                                if (TableDataController.mAssetInfoTable.getValueAt(j, 6).equals("借出")) {
                                    flag = false;
                                    break;
                                }
                            }
                        }   
                    }
                }
            }

            if (!flag) {
                String message = LM.get("ams.application.windows.dialog.borrow-conflict-data");
                // 创建按钮
                Object[] options = { LM.get("ams.application.windows.dialog.confirm") };
                JOptionPane.showOptionDialog(parent, message, waringTitle, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            } else {
                int option = JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);

                if (option == JOptionPane.YES_OPTION && table.getSelectedRows().length != 0) {
                    // 删除选中的行
                    TableDataController.mStaffInfoTable.removeRow(row);

                    ntf = NotificationManager.showNotification(parent,
                            LM.get("ams.application.windows.dialog.notification.delete"));
                } else {
                    ntf = NotificationManager.showNotification(parent,
                            LM.get("ams.application.windows.dialog.notification.close"));
                }
            }
        } else {
            waringMessage = LM.get("ams.application.windows.dialog.no-select-data");
            waringOption = new Object[1];
            waringOption[0] = LM.get("ams.application.windows.dialog.confirm");

            JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
        }

        // Restore dialog decoration if needed
        JDialog.setDefaultLookAndFeelDecorated(false);

        // 设置背景透明
        ntf.setOpaque(false);
        ntf.setDisplayTime(3000);
    }
}