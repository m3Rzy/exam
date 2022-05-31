package org.hoiboi.Util;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel<T> extends AbstractTableModel {
//    дописать класс + сделать swing


    @Override
    public String getColumnName(int column) {
        return super.getColumnName(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
