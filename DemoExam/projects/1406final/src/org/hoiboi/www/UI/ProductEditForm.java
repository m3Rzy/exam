package org.hoiboi.www.UI;

import org.hoiboi.www.Entity.ProductEntity;
import org.hoiboi.www.Manager.ProductManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ProductEditForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField titleField;
    private JTextField typeField;
    private JTextField descField;
    private JTextField pathField;
    private JTextField costField;
    private JButton backButton;
    private JButton deleteButton;
    private JButton saveButton;
    private ProductEntity product;

    public ProductEditForm(ProductEntity product)
    {
        super(1000, 600);
        setContentPane(mainPanel);
        this.product = product;
        initFields();
        initButtons();
        setVisible(true);
    }

    private void initButtons()
    {
        backButton.addActionListener(e -> {
            dispose();
            new ProductTableForm();
        });

        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Вы точно хотите удалить продукт?",
                    "Удаление продукта",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                try {
                    ProductManager.delete(product);
                    DialogUtil.showInfo(this, "Продукт был успешно удалён!");
                    dispose();
                    new ProductTableForm();
                } catch (SQLException ex) {
                    DialogUtil.showError(this, "Ошибка удаления продукта: " + ex.getMessage());
                }
            }
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

            product.setTitle(title);
            product.setType(type);
            product.setDesc(desc);
            product.setPath(path);
            product.setCost(cost);

            try {
                ProductManager.update(product);
                DialogUtil.showInfo(this, "Продукт был успешно изменён!");
                dispose();
                new ProductTableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка изменения продукта: " + ex.getMessage());
            }
        });
    }

    private void initFields()
    {
        idField.setText(String.valueOf(product.getId()));
        idField.setEditable(false);
        titleField.setText(product.getTitle());
        typeField.setText(product.getType());
        descField.setText(product.getDesc());
        pathField.setText(product.getPath());
        costField.setText(String.valueOf(product.getCost()));
    }
}
