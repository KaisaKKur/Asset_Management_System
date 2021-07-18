package ams.server.local.table;

import javax.swing.table.DefaultTableModel;

import com.alee.managers.language.LM;

import ams.controller.data.cache.TableCache;

public class TableData {

    public DefaultTableModel assetCategoryTable() {
        // 表头（列名）
        final Object[] columnNames = { LM.get("ams.application.windows.tables.asset.column-name-id"),
                LM.get("ams.application.windows.tables.asset.column-name-big-category"),
                LM.get("ams.application.windows.tables.asset.column-name-small-category") };

        // 表格默认所有行数据
        // final Object[][] rowData = { { "1", "办公用品", "计算机" }, { "2", "办公用品", "打印机" },
        // { "3", "办公用品", "扫描仪" },
        // { "4", "办公用品", "服务器" }, { "5", "耗材", "硒鼓" }, { "6", "耗材", "墨盒" }, { "7",
        // "实验用品", "光谱仪" },
        // { "8", "实验用品", "摇床" } };

        // 缓存文件读取数据
        final Object[][] rowData = TableCache.inputTableCache("AssetCategory.dat");

        return new DefaultTableModel(rowData, columnNames) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };

    }

    public DefaultTableModel assetInfoTable() {
        // 表头（列名）
        final Object[] columnNames = { LM.get("ams.application.windows.tables.asset-info.column-name-id"),
                LM.get("ams.application.windows.tables.asset-info.column-name"),
                LM.get("ams.application.windows.tables.asset-info.column-name-type"),
                LM.get("ams.application.windows.tables.asset-info.column-name-model"),
                LM.get("ams.application.windows.tables.asset-info.column-name-price"),
                LM.get("ams.application.windows.tables.asset-info.column-name-buy-date"),
                LM.get("ams.application.windows.tables.asset-info.column-name-status"),
                LM.get("ams.application.windows.tables.asset-info.column-name-other") };

        // 表格默认所有行数据
        // final Object[][] rowData = {
        // {"weqw", "sdasd", "sdsada", "sdasda", "sdfasf", "sdasd", "sdad", "sdas"}
        // };

        // 缓存文件读取数据
        final Object[][] rowData = TableCache.inputTableCache("AssetInfo.dat");

        // 缓存文件读取数据
        final Object[][] rowTypeData = TableCache.inputTableCache("AssetCategory.dat");

        // 配对类型
        String type = null;

        for (int i = 0; i < rowData.length; i++) {
            for (int j = 0; j < rowTypeData.length; j++) {
                if (rowData[i][2].equals(rowTypeData[j][0])) {
                    type = rowTypeData[j][0] + "-" + rowTypeData[j][1] + "-" + rowTypeData[j][2];
                    rowData[i][2] = type;
                }
            }
        }

        return new DefaultTableModel(rowData, columnNames) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 2 || column == 6) {
                    return false;
                } else {
                    return true;
                }
            }
        };
    }

    public DefaultTableModel setStaffInfoTable() {
        // 表头（列名）
        final Object[] columnNames = { LM.get("ams.application.windows.tables.staff-info.column-name-id"),
                LM.get("ams.application.windows.tables.staff-info.column-name"),
                LM.get("ams.application.windows.tables.staff-info.column-name-sex"),
                LM.get("ams.application.windows.tables.staff-info.column-name-dept"),
                LM.get("ams.application.windows.tables.staff-info.column-name-job"),
                LM.get("ams.application.windows.tables.staff-info.column-name-other") };

        // 缓存文件读取数据
        final Object[][] rowData = TableCache.inputTableCache("StaffInfo.dat");

        return new DefaultTableModel(rowData, columnNames) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };
    }

    public DefaultTableModel setAssetEmployCommonTable(String status) {
        // 表头（列名）
        final Object[] columnNames = { LM.get("ams.application.windows.tables.asset-info.column-name-id"),
                LM.get("ams.application.windows.tables.asset-info.column-name"),
                LM.get("ams.application.windows.tables.asset-info.column-name-type"),
                LM.get("ams.application.windows.tables.asset-info.column-name-model"),
                LM.get("ams.application.windows.tables.asset-info.column-name-price"),
                LM.get("ams.application.windows.tables.asset-info.column-name-status"), };

        // 缓存文件读取数据
        final Object[][] rowDataCache = TableCache.inputTableCache("AssetInfo.dat");
        // 缓存文件读取数据
        final Object[][] rowTypeData = TableCache.inputTableCache("AssetCategory.dat");

        // 配对类型
        String type = null;
        // 获取在库行数
        int countRow = 0;

        for (int i = 0; i < rowDataCache.length; i++) {
            for (int j = 0; j < rowTypeData.length; j++) {
                if (rowDataCache[i][2].equals(rowTypeData[j][0])) {
                    type = rowTypeData[j][0] + "-" + rowTypeData[j][1] + "-" + rowTypeData[j][2];
                    rowDataCache[i][2] = type;
                }
            }

            if (rowDataCache[i][6].equals(status)) {
                countRow++;
            }
        }

        Object[][] rowData = new Object[countRow][6];

        for (int i = 0, j = 0; i < rowDataCache.length; i++) {
            if (rowDataCache[i][6].equals(status)) {
                rowData[j][0] = rowDataCache[i][0];
                rowData[j][1] = rowDataCache[i][1];
                rowData[j][2] = rowDataCache[i][2];
                rowData[j][3] = rowDataCache[i][3];
                rowData[j][4] = rowDataCache[i][4];
                rowData[j][5] = rowDataCache[i][6];

                j++;
            }
        }

        return new DefaultTableModel(rowData, columnNames);
    }

    public DefaultTableModel employQueryTable() {
        // 表头（列名）
        final Object[] columnNames = { LM.get("ams.application.windows.tables.asset-info.column-name-id"),
                LM.get("ams.application.windows.tables.asset-employ.column-name-type"),
                LM.get("ams.application.windows.tables.asset-employ.column-name-date"),
                LM.get("ams.application.windows.tables.asset-employ.column-name-type-staff"),
                LM.get("ams.application.windows.tables.asset-employ.column-name-type-reason"),
                LM.get("ams.application.windows.tables.asset-employ.column-name-other"), };

        // 缓存文件读取数据
        final Object[][] rowData = TableCache.inputTableCache("AssetEmploy.dat");

        return new DefaultTableModel(rowData, columnNames);
    }
}