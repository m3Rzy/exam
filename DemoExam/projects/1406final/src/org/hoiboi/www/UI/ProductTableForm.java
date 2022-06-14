package org.hoiboi.www.UI;

import org.hoiboi.www.App;
import org.hoiboi.www.Entity.ProductEntity;
import org.hoiboi.www.Manager.ProductManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.CustomTableModel;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductTableForm extends BaseForm {
    private boolean costSort = false;
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JButton costSortButton;
    private JComboBox filterTypeBox;
    private JLabel counterLabel;
    private JButton helpButton;
    private JButton exitButton;
    private JButton feedbackButton;
    private CustomTableModel<ProductEntity> model;

    public ProductTableForm()
    {
        super(1200, 800);
        setContentPane(mainPanel);
        initTable();
        initButton();
        initSort();
        initFilter();
        setVisible(true);
    }

    private void initButton()
    {
        feedbackButton.addActionListener(e -> {
            DialogUtil.showInfo(this, "написать разработчику: gentledemeiz@gmail.com");
            return;
        });

        exitButton.addActionListener(e -> {
            dispose();
            new AuthForm();
        });

        helpButton.addActionListener(e -> {
            DialogUtil.showInfo(this, "=== Помощь ===\n" +
                    "1. Редактирование и удаление продукта работает по двойному нажатию ЛКМ по продукту из таблицы.\n" +
                    "2. Редактирование, удаление, добавление доступны только Администратору.");
            return;
        });

        if(App.IS_ADMIN)
        {
            addButton.setEnabled(true);
        }
        else {
            addButton.setEnabled(false);
        }
        addButton.addActionListener(e -> {
            dispose();
            new ProductCreateForm();
        });
    }

    private void initFilter()
    {
        filterTypeBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                try {
                    List<ProductEntity> list = ProductManager.selectAll();
                    int max = list.size();
                    switch(filterTypeBox.getSelectedIndex())
                    {
                        case 1:
                            list.removeIf(s -> s.getType().equals(" Маски"));
                            break;
                        case 2:
                            list.removeIf(s -> s.getType().equals(" Полумаски"));
                            break;
                    }
                    model.setRows(list);
                    model.fireTableDataChanged();
                    costSort = false;
                    updateCountRows(list.size(), max);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initSort()
    {
        costSortButton.addActionListener(e -> {
            Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                @Override
                public int compare(ProductEntity o1, ProductEntity o2) {
                    if(costSort)
                    {
                        return Double.compare(o2.getCost(), o1.getCost());
                    } else
                    {
                        return Double.compare(o1.getCost(), o2.getCost());
                    }
                }
            });
            costSort = !costSort;
            model.fireTableDataChanged();
        });
    }

    private void updateCountRows(int actual, int max)
    {
        counterLabel.setText("Отображено записей: " + actual + " / " + max);
    }

    private void initTable()
    {
        if(App.IS_ADMIN)
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
                            new ProductEditForm(model.getRows().get(row));
                        }
                    }
                }
            });
        }


        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        try {
            model = new CustomTableModel<>(
              ProductEntity.class,
              new String[]{"ID", "Название", "Тип продукта", "Описание", "Путь до картинки", "Цена", "Картинка"},
                    ProductManager.selectAll()
            );
            table.setModel(model);
            updateCountRows(model.getRows().size(), model.getRows().size());
            TableColumn column = table.getColumn("Путь до картинки");
            column.setMaxWidth(0);
            column.setMinWidth(0);
            column.setPreferredWidth(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
