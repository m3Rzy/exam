package org.hoiboi.www.UI;

import org.hoiboi.www.App;
import org.hoiboi.www.Entity.ClientEntity;
import org.hoiboi.www.Manager.ClientManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JLabel counterRowsLabel;
    private JButton addButton;
    private JButton sortCashButton;
    private JComboBox filterGenderBox;
    private CustomTableModel<ClientEntity> model;
    private static boolean sortButton = false;

    public ClientTableForm()
    {
        super(1200, 800);
        setContentPane(mainPanel);
        initTable();
        initButtons();
        setVisible(true);
    }

    private void initButtons()
    {
        filterGenderBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                try {
                    List<ClientEntity> list = ClientManager.selectAll();
                    int max = list.size();

                    switch (filterGenderBox.getSelectedIndex())
                    {
                        case 1:
                            list.removeIf(s -> s.getGender() == 'м');
                            break;

                        case 2:
                            list.removeIf(s -> s.getGender() == 'ж');
                            break;
                    }
                    sortButton = false;
                    model.setRows(list);
                    model.fireTableDataChanged();
                    counterRows(model.getRows().size(), max);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        sortCashButton.addActionListener(e -> {
            Collections.sort(model.getRows(), new Comparator<ClientEntity>() {
                @Override
                public int compare(ClientEntity o1, ClientEntity o2) {
                    if(sortButton)
                    {
                        return Double.compare(o2.getCash(), o1.getCash());
                    } else {
                        return Double.compare(o1.getCash(), o2.getCash());
                    }
                }
            });
            sortButton = !sortButton;
            model.fireTableDataChanged();
        });

        if(App.IS_ADMIN == false)
        {
            addButton.setEnabled(false);
        }

        addButton.addActionListener(e -> {
            dispose();
            new ClientCreateForm();
        });
    }

    private void counterRows(int actual, int max)
    {
        counterRowsLabel.setText("Отображено записей: " + actual + " / " + max);
    }

    private void initTable()
    {
        if(App.IS_ADMIN)
        {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2) {
                        int row = table.rowAtPoint(e.getPoint());
                        if(row != -1) {
                            dispose();
                            new ClientEditForm(model.getRows().get(row));
                        }
                    }
                }
            });
        }


        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        try {
            model = new CustomTableModel<>(
                    ClientEntity.class,
                    new String[]{"ID", "ФИО", "Номер телефона", "Гендер", "Дата регистрации", "День рождения", "Сумма", "Путь до картинки", "Картинка"},
                    ClientManager.selectAll()
            );
            table.setModel(model);
            TableColumn column = table.getColumn("Путь до картинки");
            column.setMaxWidth(0);
            column.setMinWidth(0);
            column.setPreferredWidth(0);
            counterRows(model.getRows().size(), model.getRows().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
