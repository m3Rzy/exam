package org.hoiboi.www.UI;

import org.hoiboi.www.Entity.ProductEntity;
import org.hoiboi.www.Manager.ProductManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ProductCreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField typeField;
    private JTextField descField;
    private JTextField pathField;
    private JTextField costField;
    private JButton saveButton;
    private JButton backButton;

    public ProductCreateForm()
    {
        super(800, 400);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    private void initButtons()
    {

        backButton.addActionListener(e -> {
            dispose();
            new ProductTableForm();
        });

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            if(title.isEmpty() || title.length() > 255)
            {
                DialogUtil.showError(this, "Название не может быть пустым или больше 255 символов.");
                return;
            }
            String type = typeField.getText();
            if(type.isEmpty() || type.length() > 255)
            {
                DialogUtil.showError(this, "Тип продукта не может быть пустым или больше 255 символов.");
                return;
            }

            String desc = descField.getText();

            String path = pathField.getText();

            double cost = -1;
            cost = Double.parseDouble(costField.getText());
            if(cost <= -1)
            {
                DialogUtil.showError(this, "Цена не может быть пустой или меньше 0.");
                return;
            }

            ProductEntity product = new ProductEntity(
                    title, type, desc, path, cost
            );

            try {
                ProductManager.insert(product);
                DialogUtil.showInfo(this, "Продукта был успешно добавлен!");
                dispose();
                new ProductTableForm();
            } catch (Exception ex) {
                DialogUtil.showError(this, "Ошибка добавления продукта: " + ex.getMessage());
            }
        });


    }
}
