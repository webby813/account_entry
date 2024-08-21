package db_objects;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomTableModel extends AbstractTableModel {
    private String[] columnNames = {"Item", "Quantity", "Price per item", "Total"};
    private List<Object[]> data = new ArrayList<>();

    public CustomTableModel() {
        // Initialize with a few empty rows
        setRowCount(3);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true; // Modify this if you need specific editability rules
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        // Optional: Add validation here
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setRowCount(int rowCount) {
        int currentRows = getRowCount();
        if (rowCount > currentRows) {
            // Add new rows
            for (int i = currentRows; i < rowCount; i++) {
                data.add(new Object[getColumnCount()]);
            }
        } else if (rowCount < currentRows) {
            // Remove rows
            for (int i = currentRows - 1; i >= rowCount; i--) {
                data.remove(i);
            }
        }
        fireTableDataChanged();
    }

    public void addRow(Object[] rowData) {
        if (rowData.length == getColumnCount()) { // Ensure rowData matches column count
            data.add(rowData);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        } else {
            throw new IllegalArgumentException("Row data does not match column count.");
        }
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
        fireTableStructureChanged();
    }
}
