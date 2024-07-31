package db_objects;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {
    private String[] columnNames = {"Item", "Quantity", "Price per item", "Total"};
    private Object[][] data = {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
    };

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}