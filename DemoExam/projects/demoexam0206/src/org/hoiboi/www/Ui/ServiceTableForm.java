package org.hoiboi.www.Ui;

import org.hoiboi.www.Entity.ServiceEntity;
import org.hoiboi.www.Manager.ServiceEntityManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.SQLException;

public class ServiceTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private CustomTableModel<ServiceEntity> model;

    public ServiceTableForm()
    {
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
                    new String[]{"ID", "Название", "Цена", "Длительность", "Комментарий", "Скидка", "Путь до картинки"},
                    ServiceEntityManager.selectAll()
            );
            table.setModel(model);


            TableColumn column = table.getColumn("Путь до картинки");
            column.setMinWidth(0);
            column.setPreferredWidth(0);
            column.setMaxWidth(0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
