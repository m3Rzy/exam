package org.hoiboi.www.Ui;

import org.hoiboi.www.Entity.ServiceEntity;
import org.hoiboi.www.Manager.ServiceEntityManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServiceTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private CustomTableModel<ServiceEntity> model;

    public ServiceTableForm()
    {
        super(1200, 800);
        setContentPane(mainPanel);

        initTable();
        initButtons();
        setVisible(true);
    }

    public void initButtons()
    {
        addButton.addActionListener(e -> {
            dispose();
            new ServiceCreateForm();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if(row != -1) {
                        dispose();
                        new ServiceEditForm(model.getRows().get(row));
                    }
                }
            }
        });
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
