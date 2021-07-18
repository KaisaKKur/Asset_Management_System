package ams.client.menu;

import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;

public class Common {
    public static void newFilter(WebTable table, WebTextField filterText, TableRowSorter<?> sorter, int column) {
        RowFilter<TableModel, Object> rf = null;
        // If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), column);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);

        if (table.getRowCount() != 0) {
            table.setSelectedRow(0);
        }
    }
}