package ams.controller.data.table;

import javax.swing.table.DefaultTableModel;

import ams.controller.data.cache.TableCache;
import ams.server.local.table.TableData;

public class TableDataController {
    public static DefaultTableModel mAssetCategoryTable = setAssetCategoryTable();
    public static DefaultTableModel mAssetInfoTable = setAssetInfoTable();
    public static DefaultTableModel mStaffInfoTable = setStaffInfoTable();
    public static DefaultTableModel mBorrowEmployTable = setBorrowEmployTable();
    public static DefaultTableModel mRemandEmployTable = setRemandEmployTable();
    public static DefaultTableModel mScrapEmployTable = setScrapEmployTable();
    public static DefaultTableModel mEmployQueryTable = setEmployQueryTable();
    public static String[] mStaffFormate = getStaffFormate();
    public static String[] mAssetTypeFormate = getAssetTypeFormate();

    private static DefaultTableModel setAssetCategoryTable() {
        return new TableData().assetCategoryTable();
    }

    private static DefaultTableModel setScrapEmployTable() {
        return new TableData().setAssetEmployCommonTable("在库");
    }

    private static DefaultTableModel setEmployQueryTable() {
        return new TableData().employQueryTable();
    }

    public static DefaultTableModel setAssetInfoTable() {
        return new TableData().assetInfoTable();
    }

    public static DefaultTableModel setStaffInfoTable() {
        return new TableData().setStaffInfoTable();
    }

    public static DefaultTableModel setBorrowEmployTable() {
        return new TableData().setAssetEmployCommonTable("在库");
    }

    public final static DefaultTableModel setRemandEmployTable() {
        return new TableData().setAssetEmployCommonTable("借出");
    }

    public static void updateCache(String[][] data, String file) {
        TableCache.outputTableCache(data, file);
    }

    public static String[][] getAssetCategoryTableData() {
        return getCommonTableData(TableDataController.mAssetCategoryTable);
    }

    public static String[][] getStaffInfoTableData() {
        return getCommonTableData(TableDataController.mStaffInfoTable);
    }

    public static String[][] getEmployQueryTableData() {
        return getCommonTableData(TableDataController.mEmployQueryTable);
    }

    public static String[][] getAssetInfoTableData() {
        String[][] data = new String[TableDataController.mAssetInfoTable
                .getRowCount()][TableDataController.mAssetInfoTable.getColumnCount()];

        // 获取表格数据 进行配对
        for (int i = 0; i < TableDataController.mAssetInfoTable.getRowCount(); i++) {
            for (int j = 0; j < TableDataController.mAssetInfoTable.getColumnCount(); j++) {
                if (j == 2) {
                    data[i][j] = ((String) TableDataController.mAssetInfoTable.getValueAt(i, j)).substring(0, 3);
                } else {
                    data[i][j] = (String) TableDataController.mAssetInfoTable.getValueAt(i, j);
                }
            }
        }

        return data;
    }

    public static String[] getAssetTypeFormate() {
        // 获取类型
        String[] types = new String[TableDataController.mAssetCategoryTable.getRowCount()];
        String string;

        for (int i = 0; i < TableDataController.mAssetCategoryTable.getRowCount(); i++) {
            for (int j = 0; j < TableDataController.mAssetCategoryTable.getColumnCount(); j++) {
                if (j == 0) {
                    types[i] = (String) TableDataController.mAssetCategoryTable.getValueAt(i, j);
                } else {
                    string = (String) TableDataController.mAssetCategoryTable.getValueAt(i, j);
                    types[i] += ("-" + string);
                }
            }
        }

        return types;
    }

    public static void matchAssetType() {
        String string = null;

        for (int i = 0; i < mAssetInfoTable.getRowCount(); i++) {
            for (int j = 0; j < mAssetCategoryTable.getRowCount(); j++) {
                if (((String) mAssetInfoTable.getValueAt(i, 2)).substring(0, 3)
                        .equals(mAssetCategoryTable.getValueAt(j, 0))) {
                    for (int k = 0; k < TableDataController.mAssetCategoryTable.getColumnCount(); k++) {
                        if (k == 0) {
                            string = (String) TableDataController.mAssetCategoryTable.getValueAt(j, k);
                        } else {
                            string += ("-" + (String) TableDataController.mAssetCategoryTable.getValueAt(j, k));
                        }
                    }
                    mAssetInfoTable.setValueAt(string, i, 2);
                }
            }

        }

    }

    public static String[][] getCommonTableData(DefaultTableModel defaultTableModel) {
        String[][] data = new String[defaultTableModel.getRowCount()][defaultTableModel.getColumnCount()];

        // 获取表格数据
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            for (int j = 0; j < defaultTableModel.getColumnCount(); j++) {
                data[i][j] = (String) defaultTableModel.getValueAt(i, j);
            }
        }

        return data;
    }

	public static String[] getStaffFormate() {
		// 获取类型
        String[] types = new String[TableDataController.mStaffInfoTable.getRowCount()];
        String string;

        for (int i = 0; i < TableDataController.mStaffInfoTable.getRowCount(); i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    continue;
                }

                if (j == 0) {
                    types[i] = (String) TableDataController.mStaffInfoTable.getValueAt(i, j);
                } else {
                    string = (String) TableDataController.mStaffInfoTable.getValueAt(i, j);
                    types[i] += ("-" + string);
                }
            }
        }

        return types;
    }
    
    public static void updateStaffFormate() {
		// 获取类型
        mStaffFormate = getStaffFormate();
    }
    
    public static void updateAssetTypeFormate() {
		// 获取类型
        mAssetTypeFormate = getAssetTypeFormate();
	}

	public static void updateAssetEmployCache(String[] data, String file) {
        TableCache.outputTableCache(data, file);
	}
}