package org.hoiboi.ww.UI;

import org.hoiboi.ww.Entity.ServiceEntity;
import org.hoiboi.ww.Manager.ServiceManager;
import org.hoiboi.ww.Util.BaseForm;
import org.hoiboi.ww.Util.CustomTableModel;
import org.hoiboi.ww.Util.DialogUtil;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JLabel counterRowsLabel;
    private JButton sortDiscountButton;
    private JComboBox filterCostBox;
    private JComboBox filterDiscountBox;

    private CustomTableModel<ServiceEntity> model;
    private boolean discountSort = false;

    public ServiceTableForm()
    {
        super(1200, 800);
        setContentPane(mainPanel);
        initTable();
        initButtons();
        setVisible(true);
    }



    private void counterRows(int actual, int max)
    {
        counterRowsLabel.setText("Отображено записей: " + actual + " / " + max);
    }

    private void initButtons()
    {
        addButton.addActionListener(e -> {
            dispose();
            new ServiceCreateForm();
        });

        sortDiscountButton.addActionListener(e -> {
            Collections.sort(model.getRows(), new Comparator<ServiceEntity>() {
                @Override
                public int compare(ServiceEntity o1, ServiceEntity o2) {
                    if(discountSort)
                    {
                        return Integer.compare(o2.getDiscount(), o1.getDiscount());
                    } else {
                        return Integer.compare(o1.getDiscount(), o2.getDiscount());
                    }
                }
            });
            discountSort = !discountSort;
            model.fireTableDataChanged();
        });

        filterDiscountBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                try {
                    List<ServiceEntity> list = ServiceManager.selectAll();
                    int max = list.size();

                    switch(filterDiscountBox.getSelectedIndex())
                    {
                        case 1:
                            list.removeIf(s -> s.getCost() > 1500);
                            break;
                        case 2:
                            list.removeIf(s -> s.getCost() > 2000);
                            break;
                        case 3:
                            list.removeIf(s -> s.getCost() >= 1000);
                            break;
                    }
                    model.setRows(list);
                    model.fireTableDataChanged();

                    discountSort = false;

                    counterRows(list.size(), max);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    DialogUtil.showError(this, "Ошибка получения данных");
                }
            }
        });
    }

    private void initTable()
    {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    int row = table.rowAtPoint(e.getPoint());
                    if(row != -1)
                    {
                        dispose();
                        new ServiceEditForm(model.getRows().get(row));
                    }
                }
            }
        });

        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        try {
            model = new CustomTableModel<>(
                    ServiceEntity.class,
                    new String[]{"ID", "Название", "Цена", "Длительность", "Описание", "Скидка", "Путь до картинки", "Картинка"},
                    ServiceManager.selectAll()
            );
            table.setModel(model);

            TableColumn column = table.getColumn("Путь до картинки");
            column.setPreferredWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            counterRows(model.getRows().size(), model.getRows().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
