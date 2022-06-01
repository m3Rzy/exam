package org.hoiboi.Util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;
@AllArgsConstructor
public class CustomTableModel<T> extends AbstractTableModel {
    private Class<T> cls;
    private String[] columnNames;
    private List<T> rows;

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        return column < columnNames.length ? columnNames[column] : "*Название*";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ОШИБКа";
        }
    }

}
