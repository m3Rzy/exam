package org.hoiboi.UI;

import org.hoiboi.Entity.ServiceEntity;
import org.hoiboi.Manager.ServiceManager;
import org.hoiboi.Util.BaseForm;
import org.hoiboi.Util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.SQLException;

public class ServiceTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private CustomTableModel<ServiceEntity> model;

    public ServiceTableForm() {
        super(1200, 800);
        setContentPane(mainPanel);

        initTable();

        setVisible(true);
    }

    public void initTable()
    {
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);

        try {
            model = new CustomTableModel<>(
                    ServiceEntity.class,
                    new String[] {"ID", "Название", "Стоимость", "Длительность", "Описание", "Скидка", "Путь до картинки" },
                    ServiceManager.selectAll()
            );
            table.setModel(model);
            TableColumn column = table.getColumn("Путь до картинки");
            column.setMinWidth(0);
            column.setPreferredWidth(0);
            column.setMaxWidth(0);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
