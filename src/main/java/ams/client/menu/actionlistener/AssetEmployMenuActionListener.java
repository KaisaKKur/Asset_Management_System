package ams.client.menu.actionlistener;

import java.awt.Color;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
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

import ams.client.App;
import ams.client.menu.Common;
import ams.controller.data.table.TableDataController;

public class AssetEmployMenuActionListener {
	public static final JComboBox<String> JComboBoxStaffBorrow = new JComboBox<>(TableDataController.mStaffFormate);
	public static final JComboBox<String> JComboBoxStaffScrap = new JComboBox<>(TableDataController.mStaffFormate);

	public ActionListener borrowEmploy() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ????????????
				final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mBorrowEmployTable);
				// ?????????????????????
				final WebTable table = new WebTable(StyleId.table, TableDataController.mBorrowEmployTable);
				table.setEditable(false);
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

				/**
				 * ??????: boxEast
				 */
				final WebPanel boxEast = new WebPanel();
				boxEast.setLayout(new BoxLayout(boxEast, BoxLayout.Y_AXIS));

				// ????????? ?????????????????? ??? box ???????????????
				// boxEast.add(Box.createHorizontalStrut(0));
				boxEast.add(Box.createVerticalStrut(200));

				final WebPanel[] webPanelAll = new WebPanel[4];

				// ????????????
				// ????????????
				final WebLabel webLabelStaff = new WebLabel(
						LM.get("ams.application.windows.tables.asset-employ.column-name-type-staff"));
				webLabelStaff.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
				JComboBoxStaffBorrow.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.combobox);

				webPanelAll[0] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[0].add(webLabelStaff, BorderLayout.WEST);
				webPanelAll[0].add(JComboBoxStaffBorrow, BorderLayout.EAST);

				// ??????
				final WebLabel webLabelUse = new WebLabel(
						LM.get("ams.application.windows.tables.asset-employ.column-name-use"));
				webLabelUse.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
				final WebTextField webTextFieldUse = new WebTextField(StyleId.textfield, 20);
				webTextFieldUse.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));

				webPanelAll[1] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[1].add(webLabelUse, BorderLayout.WEST);
				webPanelAll[1].add(webTextFieldUse, BorderLayout.EAST);

				// ??????
				final WebLabel webLabelOther = new WebLabel(
						LM.get("ams.application.windows.tables.asset-info.column-name-other"));
				final WebTextArea webTextAreaOther = new WebTextArea(StyleId.textareaDecorated, 3, 20);
				webTextAreaOther.setLineWrap(true);
				webTextAreaOther.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
				webPanelAll[2] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[2].add(webLabelOther, BorderLayout.WEST);
				webPanelAll[2].add(webTextAreaOther, BorderLayout.EAST);

				// ????????????
				// ????????????
				final ImageIcon borrowIcon = new ImageIcon(
						AssetEmployMenuActionListener.class.getResource("/ams/client/images/icons/menu/new.png"));
				final JButton borrow = new JButton(LM.get("ams.application.windows.button.borrow"), borrowIcon);
				borrow.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
				webPanelAll[3] = new WebPanel(StyleId.panel);
				webPanelAll[3].add(borrow, BorderLayout.EAST);
				borrow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// ?????????????????????
						final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
						// Setup dialog decoration
						JDialog.setDefaultLookAndFeelDecorated(true);
						// ??????????????????
						@NotNull
						WebNotification<?> ntf = new WebNotification<>();

						// ????????????
						final String closed = LM.get("ams.application.windows.dialog.notification.close");
						final String title = LM.get("ams.application.windows.dialog.title.borrow");
						final String message;

						if (table.getSelectedRows().length != 0) {
							message = LM.get("ams.application.windows.dialog.borrow");

							if (JComboBoxStaffBorrow.getSelectedIndex() < 0
									|| webTextFieldUse.getText().trim().equals("")) {
								final String waringMessage = LM.get("ams.application.windows.dialog.operation-data");
								// ????????????
								final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
								JOptionPane.showOptionDialog(parent, waringMessage, title, JOptionPane.YES_OPTION,
										JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
								ntf = NotificationManager.showNotification(parent, closed);
							} else {
								// ????????????
								final Object[] options = { LM.get("ams.application.windows.dialog.confirm"),
										LM.get("ams.application.windows.dialog.cancel") };

								int option = JOptionPane.showOptionDialog(parent, message, title,
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
										options[0]);

								if (option == JOptionPane.YES_OPTION) {
									int viewRow = table.getSelectedRow();
									int row = table.convertRowIndexToModel(viewRow);

									String string = null;

									// ???????????????????????? id ????????????
									string = (String) TableDataController.mBorrowEmployTable.getValueAt(row, 0);

									for (int i = 0; i < TableDataController.mAssetInfoTable.getRowCount(); i++) {
										if (string.equals(TableDataController.mAssetInfoTable.getValueAt(i, 0))) {
											TableDataController.mAssetInfoTable.setValueAt("??????", i, 6);
										}
									}

									TableDataController.updateCache(TableDataController.getAssetInfoTableData(),
											"AssetInfo.dat");

									String[] data = new String[6];

									data[0] = string;
									data[1] = "??????";
									// ??????????????????
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									data[2] = df.format(new Date());
									data[3] = (String) JComboBoxStaffBorrow.getSelectedItem();
									data[4] = webTextFieldUse.getText();
									data[5] = webTextAreaOther.getText();

									TableDataController.mEmployQueryTable.addRow(data);
									// ????????????
									TableDataController.updateAssetEmployCache(data, "AssetEmploy.dat");

									String[] rowData = new String[6];
									for (int i = 0; i < 6; i++) {
										rowData[i] = (String) TableDataController.mBorrowEmployTable.getValueAt(row, i);
									}
									rowData[5] = "??????";

									TableDataController.mRemandEmployTable.addRow(rowData);

									for (int i = 0; i < TableDataController.mScrapEmployTable.getRowCount(); i++) {
										if (TableDataController.mBorrowEmployTable.getValueAt(row, 0)
												.equals(TableDataController.mScrapEmployTable.getValueAt(i, 0))) {
											TableDataController.mScrapEmployTable.removeRow(i);

											break;
										}
									}

									// ????????????????????????
									TableDataController.mBorrowEmployTable.removeRow(row);

									ntf = NotificationManager.showNotification(parent,
											LM.get("ams.application.windows.dialog.notification.borrow"));
								} else {
									ntf = NotificationManager.showNotification(parent, closed);
								}
							}
						} else {
							message = LM.get("ams.application.windows.dialog.no-select-data");

							// ????????????
							final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
							JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_OPTION,
									JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
							ntf = NotificationManager.showNotification(parent, closed);
						}

						// Restore dialog decoration if needed
						JDialog.setDefaultLookAndFeelDecorated(false);

						// ??????????????????
						ntf.setOpaque(false);
						ntf.setDisplayTime(3000);
					}
				});

				boxEast.add(webPanelAll);

				WebPanel rootWebPanel = rootWebPanelCommon(table, sorter);

				// ??????????????????
				rootWebPanel.add(boxEast, BorderLayout.EAST);

				// ?????? title ??????
				final LazyIcon icon = Icons.computer;
				// ???????????????
				final String title = LM.get("ams.application.windows.tables.title-name",
						LM.get("ams.application.windows.tables.asset-employ.name-borrow"));
				// ????????????
				App.getWebDocumentPane()
						.openDocument(new DocumentData<>(title, icon, title, Color.WHITE, rootWebPanel));
			}
		};
	}

	public ActionListener remandEmploy() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ????????????
				final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mRemandEmployTable);
				// ?????????????????????
				final WebTable table = new WebTable(StyleId.table, TableDataController.mRemandEmployTable);
				table.setEditable(false);
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

				/**
				 * ??????: boxEast
				 */
				final WebPanel boxEast = new WebPanel();
				boxEast.setLayout(new BoxLayout(boxEast, BoxLayout.Y_AXIS));

				// ????????? ?????????????????? ??? box ???????????????
				// boxEast.add(Box.createHorizontalStrut(0));
				boxEast.add(Box.createVerticalStrut(200));

				final WebPanel[] webPanelAll = new WebPanel[4];

				// ???????????? ????????????
				final WebLabel webLabelStaff = new WebLabel(
						LM.get("ams.application.windows.tables.asset-employ.column-name-type-staff"));
				webLabelStaff.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
				final WebTextField webTextFieldStaff = new WebTextField(StyleId.textfield, 20);
				webTextFieldStaff.setEditable(false);

				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = table.getSelectedRow();
						if (viewRow < 0) {
							// Selection got filtered away.
							webTextFieldStaff.setText("");
						} else {
							int row = table.convertRowIndexToModel(viewRow);
							String data = "";

							for (int i = TableDataController.mEmployQueryTable.getRowCount() - 1; i >= 0; i--) {
								if (((String) TableDataController.mRemandEmployTable.getValueAt(row, 0))
										.equals((String) TableDataController.mEmployQueryTable.getValueAt(i, 0))) {
									data = (String) TableDataController.mEmployQueryTable.getValueAt(i, 3);

									break;
								}
							}

							webTextFieldStaff.setText(String.format("%s", data));
						}
					}
				});

				TableDataController.mEmployQueryTable.addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent e) {
						int type = e.getType();

						if (type == TableModelEvent.UPDATE || type == TableModelEvent.INSERT
								|| type == TableModelEvent.DELETE) {

							if (type == TableModelEvent.UPDATE) {
								int viewRow = table.getSelectedRow();
								if (viewRow < 0) {
									// Selection got filtered away.
									webTextFieldStaff.setText("");
								} else {
									int row = table.convertRowIndexToModel(viewRow);
									String data = "";

									for (int i = TableDataController.mEmployQueryTable.getRowCount() - 1; i >= 0; i--) {
										if (((String) TableDataController.mRemandEmployTable.getValueAt(row, 0)).equals(
												(String) TableDataController.mEmployQueryTable.getValueAt(i, 0))) {
											data = (String) TableDataController.mEmployQueryTable.getValueAt(i, 3);

											break;
										}
									}

									webTextFieldStaff.setText(String.format("%s", data));
								}
							}
						}
					}
				});

				webPanelAll[0] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[0].add(webLabelStaff, BorderLayout.WEST);
				webPanelAll[0].add(webTextFieldStaff, BorderLayout.EAST);

				// ????????????
				final WebLabel webLabelUse = new WebLabel(
						LM.get("ams.application.windows.tables.asset-employ.column-name-remand"));
				webLabelUse.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
				final WebTextField webTextFieldUse = new WebTextField(StyleId.textfield, 20);
				webTextFieldUse.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));

				webPanelAll[1] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[1].add(webLabelUse, BorderLayout.WEST);
				webPanelAll[1].add(webTextFieldUse, BorderLayout.EAST);

				// ??????
				final WebLabel webLabelOther = new WebLabel(
						LM.get("ams.application.windows.tables.asset-info.column-name-other"));
				final WebTextArea webTextAreaOther = new WebTextArea(StyleId.textareaDecorated, 3, 20);
				webTextAreaOther.setLineWrap(true);
				webTextAreaOther.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
				webPanelAll[2] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[2].add(webLabelOther, BorderLayout.WEST);
				webPanelAll[2].add(webTextAreaOther, BorderLayout.EAST);

				// ????????????
				// ????????????
				final ImageIcon remandIcon = new ImageIcon(
						AssetEmployMenuActionListener.class.getResource("/ams/client/images/icons/menu/reboot.png"));
				final JButton remand = new JButton(LM.get("ams.application.windows.button.remand"), remandIcon);
				remand.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
				webPanelAll[3] = new WebPanel(StyleId.panel);
				webPanelAll[3].add(remand, BorderLayout.EAST);
				remand.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// ?????????????????????
						final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
						// Setup dialog decoration
						JDialog.setDefaultLookAndFeelDecorated(true);
						// ??????????????????
						@NotNull
						WebNotification<?> ntf = new WebNotification<>();

						// ????????????
						final String closed = LM.get("ams.application.windows.dialog.notification.close");
						final String title = LM.get("ams.application.windows.dialog.title.remand");
						final String message;

						if (table.getSelectedRows().length != 0) {
							message = LM.get("ams.application.windows.dialog.remand");

							if (webTextFieldUse.getText().trim().equals("")) {
								final String waringMessage = LM.get("ams.application.windows.dialog.operation-data");
								// ????????????
								final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
								JOptionPane.showOptionDialog(parent, waringMessage, title, JOptionPane.YES_OPTION,
										JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
								ntf = NotificationManager.showNotification(parent, closed);
							} else {
								// ????????????
								final Object[] options = { LM.get("ams.application.windows.dialog.confirm"),
										LM.get("ams.application.windows.dialog.cancel") };

								int option = JOptionPane.showOptionDialog(parent, message, title,
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
										options[0]);

								if (option == JOptionPane.YES_OPTION) {
									int viewRow = table.getSelectedRow();
									int row = table.convertRowIndexToModel(viewRow);

									String string = null;

									// ???????????????????????? id ????????????
									string = (String) TableDataController.mRemandEmployTable.getValueAt(row, 0);

									for (int i = 0; i < TableDataController.mAssetInfoTable.getRowCount(); i++) {
										if (string.equals(TableDataController.mAssetInfoTable.getValueAt(i, 0))) {
											TableDataController.mAssetInfoTable.setValueAt("??????", i, 6);
										}
									}

									TableDataController.updateCache(TableDataController.getAssetInfoTableData(),
											"AssetInfo.dat");

									String[] data = new String[6];

									data[0] = string;
									data[1] = "??????";
									// ??????????????????
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									data[2] = df.format(new Date());
									data[3] = webTextFieldStaff.getText();
									data[4] = webTextFieldUse.getText();
									data[5] = webTextAreaOther.getText();

									TableDataController.mEmployQueryTable.addRow(data);
									TableDataController.updateAssetEmployCache(data, "AssetEmploy.dat");

									String[] rowData = new String[6];
									for (int i = 0; i < 6; i++) {
										rowData[i] = (String) TableDataController.mRemandEmployTable.getValueAt(row, i);
									}
									rowData[5] = "??????";

									TableDataController.mBorrowEmployTable.addRow(rowData);
									TableDataController.mScrapEmployTable.addRow(rowData);

									// ????????????????????????
									TableDataController.mRemandEmployTable.removeRow(row);

									ntf = NotificationManager.showNotification(parent,
											LM.get("ams.application.windows.dialog.notification.remand"));
								} else {
									ntf = NotificationManager.showNotification(parent, closed);
								}
							}
						} else {
							message = LM.get("ams.application.windows.dialog.no-select-data");

							// ????????????
							final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
							JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_OPTION,
									JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
							ntf = NotificationManager.showNotification(parent, closed);
						}

						// Restore dialog decoration if needed
						JDialog.setDefaultLookAndFeelDecorated(false);

						// ??????????????????
						ntf.setOpaque(false);
						ntf.setDisplayTime(3000);
					}
				});

				boxEast.add(webPanelAll);

				WebPanel rootWebPanel = rootWebPanelCommon(table, sorter);

				// ??????????????????
				rootWebPanel.add(boxEast, BorderLayout.EAST);

				// ?????? title ??????
				final LazyIcon icon = Icons.computer;
				// ???????????????
				final String title = LM.get("ams.application.windows.tables.title-name",
						LM.get("ams.application.windows.tables.asset-employ.name-remand"));
				// ????????????
				App.getWebDocumentPane()
						.openDocument(new DocumentData<>(title, icon, title, Color.WHITE, rootWebPanel));
			}
		};
	}

	public ActionListener scrappedEmpoly() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ????????????
				final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mScrapEmployTable);
				// ?????????????????????
				final WebTable table = new WebTable(StyleId.table, TableDataController.mScrapEmployTable);
				table.setEditable(false);
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

				/**
				 * ??????: boxEast
				 */
				final WebPanel boxEast = new WebPanel();
				boxEast.setLayout(new BoxLayout(boxEast, BoxLayout.Y_AXIS));

				// ????????? ?????????????????? ??? box ???????????????
				// boxEast.add(Box.createHorizontalStrut(0));
				boxEast.add(Box.createVerticalStrut(200));

				final WebPanel[] webPanelAll = new WebPanel[4];

				// ????????????
				// ????????????
				final WebLabel webLabelStaff = new WebLabel(
						LM.get("ams.application.windows.tables.asset-employ.column-name-type-staff"));
				webLabelStaff.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
				JComboBoxStaffScrap.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.combobox);

				webPanelAll[0] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[0].add(webLabelStaff, BorderLayout.WEST);
				webPanelAll[0].add(JComboBoxStaffScrap, BorderLayout.EAST);

				// ????????????
				final WebLabel webLabelUse = new WebLabel(
						LM.get("ams.application.windows.tables.asset-employ.column-name-scrap"));
				webLabelUse.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
				final WebTextField webTextFieldUse = new WebTextField(StyleId.textfield, 20);
				webTextFieldUse.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));

				webPanelAll[1] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[1].add(webLabelUse, BorderLayout.WEST);
				webPanelAll[1].add(webTextFieldUse, BorderLayout.EAST);

				// ??????
				final WebLabel webLabelOther = new WebLabel(
						LM.get("ams.application.windows.tables.asset-info.column-name-other"));
				final WebTextArea webTextAreaOther = new WebTextArea(StyleId.textareaDecorated, 3, 20);
				webTextAreaOther.setLineWrap(true);
				webTextAreaOther.setInputPrompt(LM.get("ams.application.windows.webtextfield.inputprompt.input"));
				webPanelAll[2] = new WebPanel(StyleId.panelDecorated);
				webPanelAll[2].add(webLabelOther, BorderLayout.WEST);
				webPanelAll[2].add(webTextAreaOther, BorderLayout.EAST);

				// ????????????
				// ????????????
				final ImageIcon scrapIcon = new ImageIcon(
						AssetEmployMenuActionListener.class.getResource("/ams/client/images/icons/menu/exit.png"));
				final JButton scrap = new JButton(LM.get("ams.application.windows.button.scrap"), scrapIcon);
				scrap.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.button);
				webPanelAll[3] = new WebPanel(StyleId.panel);
				webPanelAll[3].add(scrap, BorderLayout.EAST);
				scrap.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// ?????????????????????
						final Window parent = CoreSwingUtils.getNonNullWindowAncestor(App.getJFrame());
						// Setup dialog decoration
						JDialog.setDefaultLookAndFeelDecorated(true);
						// ??????????????????
						@NotNull
						WebNotification<?> ntf = new WebNotification<>();

						// ????????????
						final String closed = LM.get("ams.application.windows.dialog.notification.close");
						final String title = LM.get("ams.application.windows.dialog.title.scrap");
						final String message;

						if (table.getSelectedRows().length != 0) {
							message = LM.get("ams.application.windows.dialog.scrap");

							if (JComboBoxStaffScrap.getSelectedIndex() < 0
									|| webTextFieldUse.getText().trim().equals("")) {
								final String waringMessage = LM.get("ams.application.windows.dialog.operation-data");
								// ????????????
								final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
								JOptionPane.showOptionDialog(parent, waringMessage, title, JOptionPane.YES_OPTION,
										JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
								ntf = NotificationManager.showNotification(parent, closed);
							} else {
								// ????????????
								final Object[] options = { LM.get("ams.application.windows.dialog.confirm"),
										LM.get("ams.application.windows.dialog.cancel") };

								int option = JOptionPane.showOptionDialog(parent, message, title,
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
										options[0]);

								if (option == JOptionPane.YES_OPTION) {
									int viewRow = table.getSelectedRow();
									int row = table.convertRowIndexToModel(viewRow);

									String string = null;

									// ???????????????????????? id ????????????
									string = (String) TableDataController.mScrapEmployTable.getValueAt(row, 0);

									for (int i = 0; i < TableDataController.mAssetInfoTable.getRowCount(); i++) {
										if (string.equals(TableDataController.mAssetInfoTable.getValueAt(i, 0))) {
											TableDataController.mAssetInfoTable.setValueAt("??????", i, 6);
										}
									}

									TableDataController.updateCache(TableDataController.getAssetInfoTableData(),
											"AssetInfo.dat");

									String[] data = new String[6];

									data[0] = string;
									data[1] = "??????";
									// ??????????????????
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									data[2] = df.format(new Date());
									data[3] = (String) JComboBoxStaffScrap.getSelectedItem();
									data[4] = webTextFieldUse.getText();
									data[5] = webTextAreaOther.getText();

									TableDataController.mEmployQueryTable.addRow(data);
									// ????????????
									TableDataController.updateAssetEmployCache(data, "AssetEmploy.dat");

									for (int i = 0; i < TableDataController.mBorrowEmployTable.getRowCount(); i++) {
										if (TableDataController.mScrapEmployTable.getValueAt(row, 0)
												.equals(TableDataController.mBorrowEmployTable.getValueAt(i, 0))) {
											TableDataController.mBorrowEmployTable.removeRow(i);

											break;
										}
									}

									// ????????????????????????
									TableDataController.mScrapEmployTable.removeRow(row);

									ntf = NotificationManager.showNotification(parent,
											LM.get("ams.application.windows.dialog.notification.scrap"));
								} else {
									ntf = NotificationManager.showNotification(parent, closed);
								}
							}
						} else {
							message = LM.get("ams.application.windows.dialog.no-select-data");

							// ????????????
							final Object[] waringOption = { LM.get("ams.application.windows.dialog.confirm") };
							JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_OPTION,
									JOptionPane.WARNING_MESSAGE, null, waringOption, waringOption[0]);
							ntf = NotificationManager.showNotification(parent, closed);
						}

						// Restore dialog decoration if needed
						JDialog.setDefaultLookAndFeelDecorated(false);

						// ??????????????????
						ntf.setOpaque(false);
						ntf.setDisplayTime(3000);
					}
				});

				boxEast.add(webPanelAll);

				WebPanel rootWebPanel = rootWebPanelCommon(table, sorter);

				// ??????????????????
				rootWebPanel.add(boxEast, BorderLayout.EAST);

				// ?????? title ??????
				final LazyIcon icon = Icons.computer;
				// ???????????????
				final String title = LM.get("ams.application.windows.tables.title-name",
						LM.get("ams.application.windows.tables.asset-employ.name-scrap"));
				// ????????????
				App.getWebDocumentPane()
						.openDocument(new DocumentData<>(title, icon, title, Color.WHITE, rootWebPanel));
			}
		};
	}

	public WebPanel rootWebPanelCommon(WebTable table, TableRowSorter<?> sorter) {
		// ??? table ????????????????????? ???????????????
		final WebScrollPane wScrollPane = new WebScrollPane(table).setPreferredSize(300, 100);
		// ???????????????
		final WebPanel rootWebPanel = new WebPanel();
		// ?????????????????????????????????
		rootWebPanel.add(wScrollPane);

		// ??????????????? ????????????
		final WebPanel childWebPanel = new WebPanel();
		childWebPanel.setLayout(new BoxLayout(childWebPanel, BoxLayout.Y_AXIS));
		// ??????????????????????????????
		rootWebPanel.add(childWebPanel, BorderLayout.SOUTH);

		/**
		 * ?????????: childDownWebPanel_0
		 */
		final WebPanel childDownWebPanel_0 = new WebPanel();
		childDownWebPanel_0.setLayout(new BoxLayout(childDownWebPanel_0, BoxLayout.X_AXIS));
		// ??????????????????
		childWebPanel.add(childDownWebPanel_0);

		// ????????? ??????????????????
		childDownWebPanel_0.add(Box.createHorizontalStrut(100));

		// ID ????????????
		final WebLabel webLabelId = new WebLabel(LM.get("ams.application.windows.tables.asset-info.column-name-id"));
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

		// ????????? ??????????????????
		childDownWebPanel_0.add(Box.createHorizontalStrut(700));

		/**
		 * ?????????: childDownWebPanel_1
		 */
		final WebPanel childDownWebPanel_1 = new WebPanel();
		childDownWebPanel_1.setLayout(new BoxLayout(childDownWebPanel_1, BoxLayout.X_AXIS));
		// ??????????????????
		childWebPanel.add(childDownWebPanel_1);

		// ????????? ??????????????????
		childDownWebPanel_1.add(Box.createHorizontalStrut(100));

		// ????????????
		final WebLabel webLabelName = new WebLabel(LM.get("ams.application.windows.tables.asset-info.column-name"));
		webLabelName.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
		final WebTextField webTextFieldName = new WebTextField(StyleId.textfield, 20);
		webTextFieldName.setEditable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int viewRow = table.getSelectedRow();
				if (viewRow < 0) {
					// Selection got filtered away.
					webTextFieldName.setText("");
				} else {
					int row = table.convertRowIndexToModel(viewRow);
					final Object[] data = { table.getModel().getValueAt(row, 1) };
					webTextFieldName.setText(String.format("%s", data));
				}
			}
		});

		webLabelId.setLabelFor(webTextFieldName);
		childDownWebPanel_1.add(webLabelName);
		childDownWebPanel_1.add(webTextFieldName);

		// ????????? ??????????????????
		childDownWebPanel_1.add(Box.createHorizontalStrut(15));

		// ??????????????????
		final WebLabel webLabelType = new WebLabel(
				LM.get("ams.application.windows.tables.asset-info.column-name-type"));
		webLabelType.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
		final WebTextField webTextFieldType = new WebTextField(20);
		webTextFieldType.setEditable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int viewRow = table.getSelectedRow();
				if (viewRow < 0) {
					// Selection got filtered away.
					webTextFieldType.setText("");
				} else {
					int row = table.convertRowIndexToModel(viewRow);
					final Object[] data = { table.getModel().getValueAt(row, 2) };
					webTextFieldType.setText(String.format("%s", data));
				}
			}
		});

		webLabelId.setLabelFor(webTextFieldType);
		childDownWebPanel_1.add(webLabelType);
		childDownWebPanel_1.add(webTextFieldType);

		// ????????? ??????????????????
		childDownWebPanel_1.add(Box.createHorizontalStrut(15));

		// ????????????
		final WebLabel webLabelModel = new WebLabel(
				LM.get("ams.application.windows.tables.asset-info.column-name-model"));
		webLabelModel.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
		final WebTextField webTextFieldModel = new WebTextField(20);
		webTextFieldModel.setEditable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int viewRow = table.getSelectedRow();
				if (viewRow < 0) {
					// Selection got filtered away.
					webTextFieldModel.setText("");
				} else {
					int row = table.convertRowIndexToModel(viewRow);
					final Object[] data = { table.getModel().getValueAt(row, 3) };
					webTextFieldModel.setText(String.format("%s", data));
				}
			}
		});

		webLabelId.setLabelFor(webTextFieldModel);
		childDownWebPanel_1.add(webLabelModel);
		childDownWebPanel_1.add(webTextFieldModel);

		// ????????? ??????????????????
		childDownWebPanel_1.add(Box.createHorizontalStrut(100));

		/**
		 * ?????????: childDownWebPanel_2
		 */
		final WebPanel childDownWebPanel_2 = new WebPanel();
		childDownWebPanel_2.setLayout(new BoxLayout(childDownWebPanel_2, BoxLayout.X_AXIS));
		// ??????????????????
		childWebPanel.add(childDownWebPanel_2);

		// ????????? ??????????????????
		childDownWebPanel_2.add(Box.createHorizontalStrut(100));

		// ????????????
		final WebLabel webLabelPrice = new WebLabel(
				LM.get("ams.application.windows.tables.asset-info.column-name-price"));
		webLabelPrice.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.label);
		final WebTextField webTextFieldPrice = new WebTextField(20);
		webTextFieldPrice.setEditable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int viewRow = table.getSelectedRow();
				if (viewRow < 0) {
					// Selection got filtered away.
					webTextFieldPrice.setText("");
				} else {
					int row = table.convertRowIndexToModel(viewRow);
					final Object[] data = { table.getModel().getValueAt(row, 4) };
					webTextFieldPrice.setText(String.format("%s", data));
				}
			}
		});

		webLabelId.setLabelFor(webTextFieldPrice);
		childDownWebPanel_2.add(webLabelPrice);
		childDownWebPanel_2.add(webTextFieldPrice);

		// ????????? ??????????????????
		childDownWebPanel_2.add(Box.createHorizontalStrut(700));

		return rootWebPanel;
	}

	public ActionListener queryEmpoly() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ????????????
				final TableRowSorter<?> sorter = new TableRowSorter<>(TableDataController.mEmployQueryTable);
				List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
				// ???????????????
				sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
				sorter.setSortKeys(sortKeys);
				// ?????????????????????
				final WebTable table = new WebTable(StyleId.table, TableDataController.mEmployQueryTable);
				table.setEditable(false);
				table.optimizeColumnWidths(true);
				table.setOptimizeRowHeight(true);
				table.setPreferredScrollableViewportSize(new Dimension(500, 70));
				table.setFillsViewportHeight(true);
				table.setRowSorter(sorter);
				for (int i = 0; i < TableDataController.mEmployQueryTable.getColumnCount(); i++) {
					if (i == 2) {
						continue;
					}

					sorter.setSortable(i, false);
				}

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
				// ??? ??????????????? ?????? ?????????????????????
				table.getModel().addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent e) {
						// ?????????????????????????????????:
						// TableModelEvent.INSERT ????????????????????????
						// TableModelEvent.UPDATE ?????????????????????
						// TableModelEvent.DELETE ?????????????????????
						int type = e.getType();

						if (type == TableModelEvent.DELETE) {
							// ??????????????????
							TableDataController.updateCache(TableDataController.getEmployQueryTableData(),
									"AssetEmploy.dat");
						}
					}
				});

				// ??? table ????????????????????? ???????????????
				final WebScrollPane wScrollPane = new WebScrollPane(table).setPreferredSize(300, 100);
				// ???????????????
				final WebPanel rootWebPanel = new WebPanel();
				// ?????????????????????????????????
				rootWebPanel.add(wScrollPane);

				// ??????????????? ????????????
				final WebPanel childWebPanel = new WebPanel();
				childWebPanel.setLayout(new BoxLayout(childWebPanel, BoxLayout.Y_AXIS));
				// ??????????????????????????????
				rootWebPanel.add(childWebPanel, BorderLayout.SOUTH);

				/**
				 * ?????????: childDownWebPanel_0
				 */
				final WebPanel childDownWebPanel_0 = new WebPanel();
				childDownWebPanel_0.setLayout(new BoxLayout(childDownWebPanel_0, BoxLayout.X_AXIS));
				// ??????????????????
				childWebPanel.add(childDownWebPanel_0);

				// ????????? ??????????????????
				childDownWebPanel_0.add(Box.createHorizontalStrut(100));

				// ID ????????????
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

				// ????????? ??????????????????
				childDownWebPanel_0.add(Box.createHorizontalStrut(700));

				// ?????? title ??????
				final LazyIcon icon = Icons.computer;
				// ???????????????
				final String title = LM.get("ams.application.windows.tables.title-name",
						LM.get("ams.application.windows.tables.asset-employ.name-employ"));
				// ????????????
				App.getWebDocumentPane()
						.openDocument(new DocumentData<>(title, icon, title, Color.WHITE, rootWebPanel));
			}
		};
	}
}