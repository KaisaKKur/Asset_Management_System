package ams.client.menu.actionlistener;

import java.awt.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.alee.laf.combobox.WebComboBox;
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

public class AssetMenuActionListener {
    // 下拉列表
    public static final JComboBox<String> JComboBoxType = new JComboBox<>(TableDataController.mAssetTypeFormate);

    public ActionListener assetInfoManagement() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取数据
                final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mAssetInfoTable);
                // 创建资产信息表
                final WebTable table = new WebTable(StyleId.table, TableDataController.mAssetInfoTable);
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
                            TableDataController.updateCache(TableDataController.getAssetInfoTableData(),
                                    "AssetInfo.dat");

                            if (type == TableModelEvent.UPDATE) {
                                int viewRow = table.getSelectedRow();
                                if (viewRow >= 0) {
                                    int row = table.convertRowIndexToModel(viewRow);

                                    String id = (String) TableDataController.mAssetInfoTable.getValueAt(row, 0);

                                    for (int i = 0; i < TableDataController.mBorrowEmployTable.getRowCount(); i++) {
                                        if (id.equals(TableDataController.mBorrowEmployTable.getValueAt(i, 0))) {
                                            TableDataController.mBorrowEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 1), i, 1);
                                            TableDataController.mBorrowEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 2), i, 2);
                                            TableDataController.mBorrowEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 3), i, 3);
                                            TableDataController.mBorrowEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 4), i, 4);
                                        }
                                    }

                                    for (int i = 0; i < TableDataController.mScrapEmployTable.getRowCount(); i++) {
                                        if (id.equals(TableDataController.mScrapEmployTable.getValueAt(i, 0))) {
                                            TableDataController.mScrapEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 1), i, 1);
                                            TableDataController.mScrapEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 2), i, 2);
                                            TableDataController.mScrapEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 3), i, 3);
                                            TableDataController.mScrapEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 4), i, 4);
                                        }
                                    }

                                    for (int i = 0; i < TableDataController.mRemandEmployTable.getRowCount(); i++) {
                                        if (id.equals(TableDataController.mRemandEmployTable.getValueAt(i, 0))) {
                                            TableDataController.mRemandEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 1), i, 1);
                                            TableDataController.mRemandEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 2), i, 2);
                                            TableDataController.mRemandEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 3), i, 3);
                                            TableDataController.mRemandEmployTable.setValueAt(
                                                    TableDataController.mAssetInfoTable.getValueAt(row, 4), i, 4);
                                        }
                                    }
                                }
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
                        LM.get("ams.application.windows.tables.asset-info.column-name-id"));
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

                // 资产名称
                final WebLabel webLabelName = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name"));
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
                            final Object[] data = { TableDataController.mAssetInfoTable.getValueAt(row, 1) };
                            webTextFieldName.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldName);
                childDownWebPanel_1.add(webLabelName);
                childDownWebPanel_1.add(webTextFieldName);

                // 空白箱 分割占位组件
                childDownWebPanel_1.add(Box.createHorizontalStrut(15));

                // 资产所属类型
                final WebLabel webLabelType = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name-type"));
                webLabelType.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);

                JComboBoxType.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.combobox);
                // webComboBoxType.setPreferredHeight(30);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            JComboBoxType.setSelectedIndex(-1);
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final String id = TableDataController.mAssetInfoTable.getValueAt(row, 2).toString();
                            JComboBoxType.setSelectedItem(id);
                        }
                    }
                });

                webLabelId.setLabelFor(JComboBoxType);
                childDownWebPanel_1.add(webLabelType);
                childDownWebPanel_1.add(JComboBoxType);

                // 空白箱 分割占位组件
                childDownWebPanel_1.add(Box.createHorizontalStrut(15));

                // 资产型号
                final WebLabel webLabelModel = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name-model"));
                webLabelModel.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldModel = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldModel.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mAssetInfoTable.getValueAt(row, 3) };
                            webTextFieldModel.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldModel);
                childDownWebPanel_1.add(webLabelModel);
                childDownWebPanel_1.add(webTextFieldModel);

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

                // 资产价格
                final WebLabel webLabelPrice = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name-price"));
                webLabelPrice.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldPrice = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldPrice.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mAssetInfoTable.getValueAt(row, 4) };
                            webTextFieldPrice.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldPrice);
                childDownWebPanel_2.add(webLabelPrice);
                childDownWebPanel_2.add(webTextFieldPrice);

                // 空白箱 分割占位组件
                childDownWebPanel_2.add(Box.createHorizontalStrut(15));

                // 资产购买日期
                final WebLabel webLabelBuyDate = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name-buy-date"));
                webLabelBuyDate.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldBuyDate = new WebTextField(20);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldBuyDate.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mAssetInfoTable.getValueAt(row, 5) };
                            webTextFieldBuyDate.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldBuyDate);
                childDownWebPanel_2.add(webLabelBuyDate);
                childDownWebPanel_2.add(webTextFieldBuyDate);

                // 空白箱 分割占位组件
                childDownWebPanel_2.add(Box.createHorizontalStrut(15));

                // 资产状态
                final WebLabel webLabelStatus = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name-status"));
                webLabelStatus.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
                final WebTextField webTextFieldStatus = new WebTextField(20);
                webTextFieldStatus.setEditable(false);
                webTextFieldStatus.setForeground(Color.BLUE);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            // Selection got filtered away.
                            webTextFieldStatus.setText("");
                        } else {
                            int row = table.convertRowIndexToModel(viewRow);
                            final Object[] data = { TableDataController.mAssetInfoTable.getValueAt(row, 6) };
                            webTextFieldStatus.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldStatus);
                childDownWebPanel_2.add(webLabelStatus);
                childDownWebPanel_2.add(webTextFieldStatus);

                // 空白箱 分割占位组件
                childDownWebPanel_2.add(Box.createHorizontalStrut(100));

                /**
                 * 子盒四: childDownWebPanel_3
                 */
                final WebPanel childDownWebPanel_3 = new WebPanel();
                childDownWebPanel_3.setLayout(new BoxLayout(childDownWebPanel_3, BoxLayout.X_AXIS));
                // 添加至子窗格
                childWebPanel.add(childDownWebPanel_3);

                // 空白箱 分割占位组件
                childDownWebPanel_3.add(Box.createHorizontalStrut(100));

                // 资产备注
                final WebLabel webLabelOther = new WebLabel(
                        LM.get("ams.application.windows.tables.asset-info.column-name-other"));
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
                            final Object[] data = { TableDataController.mAssetInfoTable.getValueAt(row, 7) };
                            webTextFieldOther.setText(String.format("%s", data));
                        }
                    }
                });

                webLabelId.setLabelFor(webTextFieldOther);
                childDownWebPanel_3.add(webLabelOther);
                childDownWebPanel_3.add(webTextFieldOther);

                // 空白箱 分割占位组件
                childDownWebPanel_3.add(Box.createHorizontalStrut(15));

                // 修改
                // 加载图标
                final ImageIcon modifyIcon = new ImageIcon(
                        AssetMenuActionListener.class.getResource("/ams/client/images/icons/menu/edit.png"));
                final JButton modify = new JButton(LM.get("ams.application.windows.button.modify"), modifyIcon);
                modify.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                childDownWebPanel_3.add(modify);
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
                        final int row = table.convertRowIndexToModel(viewRow);
                        final Object[] inputData = { webTextFieldName.getText().trim(),
                                JComboBoxType.getSelectedItem().toString(), webTextFieldModel.getText().trim(),
                                webTextFieldPrice.getText().trim(), webTextFieldBuyDate.getText().trim(),
                                webTextFieldOther.getText().trim() };

                        final String waringTitle = LM.get("ams.application.windows.dialog.title.modify-data");

                        if (inputData[0].equals("") || inputData[1].equals("") || inputData[2].equals("")
                                || inputData[3].equals("") || table.getSelectedRows().length == 0) {
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
                            TableDataController.mAssetInfoTable.setValueAt(inputData[0], row, 1);
                            TableDataController.mAssetInfoTable.setValueAt(inputData[1], row, 2);
                            TableDataController.mAssetInfoTable.setValueAt(inputData[2], row, 3);
                            TableDataController.mAssetInfoTable.setValueAt(inputData[3], row, 4);
                            TableDataController.mAssetInfoTable.setValueAt(inputData[4], row, 5);
                            TableDataController.mAssetInfoTable.setValueAt(inputData[5], row, 7);

                            ntf = NotificationManager.showNotification(parent,
                                    LM.get("ams.application.windows.dialog.notification.load"));
                        }

                        // 设置背景透明
                        ntf.setOpaque(false);
                        ntf.setDisplayTime(3000);
                    }
                });

                // 空白箱 分割占位组件
                childDownWebPanel_3.add(Box.createHorizontalStrut(100));

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
                final ImageIcon addIcon = new ImageIcon(
                        AssetMenuActionListener.class.getResource("/ams/client/images/icons/menu/new.png"));
                final JButton add = new JButton(LM.get("ams.application.windows.button.add"), addIcon);
                add.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
                boxEast.add(add);
                add.setActionCommand("add");
                add.addActionListener(bottonListener);

                // 删除
                // 加载图标
                final ImageIcon deleteIcon = new ImageIcon(
                        AssetMenuActionListener.class.getResource("/ams/client/images/icons/menu/exit.png"));
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
                        LM.get("ams.application.windows.tables.asset-info.name"));
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

    /**
     * 添加资产信息
     */
    private void addAssetInfo() {
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

        if (TableDataController.mAssetCategoryTable.getRowCount() == 0) {
            final String waringTitle = LM.get("ams.application.windows.dialog.title-waring");
            final String waringMessage = LM.get("ams.application.windows.dialog.asset-category-empty");
            // 创建按钮
            final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
            JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
            ntf = NotificationManager.showNotification(parent, closed);
        } else {
            // 创建按钮
            final Object[] options = { LM.get("ams.application.windows.dialog.save"),
                    LM.get("ams.application.windows.dialog.cancel") };

            // 内容
            final WebPanel[] webPanelAll = new WebPanel[8];
            // ID
            final WebLabel webLabelId = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-id"));
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

            // 资产名称
            final WebLabel webLabelName = new WebLabel(LM.get("ams.application.windows.tables.asset-info.column-name"));
            webLabelName.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
            final WebTextField webTextFieldName = new WebTextField(StyleId.textfield, 20);
            webTextFieldName.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
            webPanelAll[1] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[1].add(webLabelName, BorderLayout.WEST);
            webPanelAll[1].add(webTextFieldName, BorderLayout.EAST);

            // 资产所属类型
            final WebLabel webLabelType = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-type"));
            webLabelType.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);

            // 获取类型
            String[] types = TableDataController.getAssetTypeFormate();

            // 下拉列表
            final WebComboBox webComboBoxType = new WebComboBox(types, StyleId.combobox);
            webComboBoxType.setPreferredHeight(30);

            webPanelAll[2] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[2].add(webLabelType, BorderLayout.WEST);
            webPanelAll[2].add(webComboBoxType, BorderLayout.SOUTH);

            // 资产型号
            final WebLabel webLabelModel = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-model"));
            webLabelModel.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
            final WebTextField webTextFieldModel = new WebTextField(StyleId.textfield, 20);
            webTextFieldModel.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
            webPanelAll[3] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[3].add(webLabelModel, BorderLayout.WEST);
            webPanelAll[3].add(webTextFieldModel, BorderLayout.EAST);

            // 资产价格
            final WebLabel webLabelPrice = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-price"));
            webLabelPrice.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
            final WebTextField webTextFieldPrice = new WebTextField(StyleId.textfield, 20);
            webTextFieldPrice.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
            webPanelAll[4] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[4].add(webLabelPrice, BorderLayout.WEST);
            webPanelAll[4].add(webTextFieldPrice, BorderLayout.EAST);

            // 资产购买日期
            final WebLabel webLabelBuyDate = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-buy-date"));
            webLabelBuyDate.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
            final WebTextField webTextFieldBuyDate = new WebTextField(StyleId.textfield, 20);
            webTextFieldBuyDate.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
            webPanelAll[5] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[5].add(webLabelBuyDate, BorderLayout.WEST);
            webPanelAll[5].add(webTextFieldBuyDate, BorderLayout.EAST);

            // 资产状态
            final WebLabel webLabelStatus = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-status"));
            webLabelStatus.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
            final WebTextField webTextFieldStatus = new WebTextField(StyleId.textfield, 20);
            webTextFieldStatus.setEditable(false);
            webTextFieldStatus.setForeground(Color.BLUE);
            webTextFieldStatus.setText("在库");
            webPanelAll[6] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[6].add(webLabelStatus, BorderLayout.WEST);
            webPanelAll[6].add(webTextFieldStatus, BorderLayout.EAST);

            // 备注
            final WebLabel webLabelOther = new WebLabel(
                    LM.get("ams.application.windows.tables.asset-info.column-name-other"));
            final WebTextArea webTextAreaOther = new WebTextArea(StyleId.textareaDecorated, 3, 20);
            webTextAreaOther.setLineWrap(true);
            webTextAreaOther.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
            webPanelAll[7] = new WebPanel(StyleId.panelDecorated);
            webPanelAll[7].add(webLabelOther, BorderLayout.WEST);
            webPanelAll[7].add(webTextAreaOther, BorderLayout.EAST);

            final Object[] message = { webPanelAll[0], webPanelAll[1], webPanelAll[2], webPanelAll[3], webPanelAll[4],
                    webPanelAll[5], webPanelAll[6], webPanelAll[7] };

            final int option = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);

            // 获取文本框数据 去掉尾部空格
            String[] inputData = new String[] { webTextFieldId.getText().trim(), webTextFieldName.getText().trim(),
                    webComboBoxType.getSelectedItem().toString(), webTextFieldModel.getText().trim(),
                    webTextFieldPrice.getText().trim(), webTextFieldBuyDate.getText().trim(),
                    webTextFieldStatus.getText().trim(), webTextAreaOther.getText() };

            if (option == JOptionPane.OK_OPTION) {
                if (inputData[1].equals("") || inputData[2].equals("") || inputData[3].equals("")
                        || inputData[4].equals("") || inputData[5].equals("") || inputData[6].equals("")) {
                    final String waringTitle = LM.get("ams.application.windows.dialog.title-waring");
                    final String waringMessage = LM.get("ams.application.windows.dialog.waring-data");
                    // 创建按钮
                    final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
                    JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
                    ntf = NotificationManager.showNotification(parent, closed);
                } else {
                    TableDataController.mAssetInfoTable.addRow(inputData);

                    String[] rowData = new String[6];
                    rowData[0] = (String) TableDataController.mAssetInfoTable
                            .getValueAt(TableDataController.mAssetInfoTable.getRowCount() - 1, 0);
                    rowData[1] = (String) TableDataController.mAssetInfoTable
                            .getValueAt(TableDataController.mAssetInfoTable.getRowCount() - 1, 1);
                    rowData[2] = (String) TableDataController.mAssetInfoTable
                            .getValueAt(TableDataController.mAssetInfoTable.getRowCount() - 1, 2);
                    rowData[3] = (String) TableDataController.mAssetInfoTable
                            .getValueAt(TableDataController.mAssetInfoTable.getRowCount() - 1, 3);
                    rowData[4] = (String) TableDataController.mAssetInfoTable
                            .getValueAt(TableDataController.mAssetInfoTable.getRowCount() - 1, 4);
                    rowData[5] = (String) TableDataController.mAssetInfoTable
                            .getValueAt(TableDataController.mAssetInfoTable.getRowCount() - 1, 6);

                    TableDataController.mBorrowEmployTable.addRow(rowData);
                    TableDataController.mScrapEmployTable.addRow(rowData);
                    
                    ntf = NotificationManager.showNotification(parent, save);
                }
            } else {
                ntf = NotificationManager.showNotification(parent, closed);
            }
        }

        // Restore dialog decoration if needed
        JDialog.setDefaultLookAndFeelDecorated(false);

        // 设置背景透明
        ntf.setOpaque(false);
        ntf.setDisplayTime(3000);
    }

    private void deleteAssetInfo(WebTable table) {
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

            if (TableDataController.mAssetInfoTable.getValueAt(row, 6).equals("借出")) {
                flag = false;
            }

            if (!flag) {
                String message = LM.get("ams.application.windows.dialog.asset-conflict-data");
                // 创建按钮
                Object[] options = { LM.get("ams.application.windows.dialog.confirm") };
                JOptionPane.showOptionDialog(parent, message, waringTitle, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            } else {
                int option = JOptionPane.showOptionDialog(parent, waringMessage, waringTitle, JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);

                if (option == JOptionPane.YES_OPTION && table.getSelectedRows().length != 0) {
                    for (int i = 0; i < TableDataController.mBorrowEmployTable.getRowCount(); i++) {
                        if (TableDataController.mAssetInfoTable.getValueAt(row, 0)
                                .equals(TableDataController.mBorrowEmployTable.getValueAt(i, 0))) {
                            TableDataController.mBorrowEmployTable.removeRow(i);
                            break;
                        }
                    }

                    for (int i = 0; i < TableDataController.mScrapEmployTable.getRowCount(); i++) {
                        if (TableDataController.mAssetInfoTable.getValueAt(row, 0)
                                .equals(TableDataController.mScrapEmployTable.getValueAt(i, 0))) {
                            TableDataController.mScrapEmployTable.removeRow(i);
                            break;
                        }
                    }

                    for (int i = 0; i < TableDataController.mEmployQueryTable.getRowCount(); i++) {
                        if (TableDataController.mAssetInfoTable.getValueAt(row, 0)
                                .equals(TableDataController.mEmployQueryTable.getValueAt(i, 0))) {
                            TableDataController.mEmployQueryTable.removeRow(i--);
                        }
                    }

                    // 删除选中的行
                    TableDataController.mAssetInfoTable.removeRow(row);

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
