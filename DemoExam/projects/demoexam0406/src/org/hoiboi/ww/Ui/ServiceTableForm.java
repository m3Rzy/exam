package org.hoiboi.ww.Ui;

import org.hoiboi.ww.Entity.ServiceEntity;
import org.hoiboi.ww.Manager.ServiceEntityManager;
import org.hoiboi.ww.Util.BaseForm;
import org.hoiboi.ww.Util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.SQLException;

public class ServiceTableForm extends BaseForm {
    private JTable table;
    private JPanel mainPanel;
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
