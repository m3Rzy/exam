package org.hoiboi.www.Ui;

import org.hoiboi.www.Entity.ServiceEntity;
import org.hoiboi.www.Manager.ServiceEntityManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ServiceCreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField costField;
    private JTextField durationField;
    private JTextField descField;
    private JTextField discountField;
    private JTextField pathField;
    private JButton addButton;
    private JButton backButton;

    public ServiceCreateForm()
    {
        super(1200, 800);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    public void initButtons()
    {
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            if(title.isEmpty() || title.length() > 50)
            {
                DialogUtil.showError(this, "Длина символов либо 0, либо превышает 50 символов.");
                return;
            }

            double cost = Double.parseDouble(costField.getText());
            if(cost < 0 || cost == 0)
            {
                DialogUtil.showError(this, "Стоимость либо меньше 0, либо 0.");
                return;
            }

            int duration = Integer.parseInt(durationField.getText());
            if(duration < 0 || duration == 0)
            {
                DialogUtil.showError(this, "Длительность либо меньше 0, либо равно 0.");
                return;
            }

            String desc = descField.getText();
            if(desc.length() > 100)
            {
                DialogUtil.showError(this, "Комментарий не может быть больше 100 символов.");
                return;
            }

            int discount = Integer.parseInt(discountField.getText());
            if(discount > 100 || discount < 0)
            {
                DialogUtil.showError(this, "Скидка не может быть больше 100, либо она не может быть меньше 0.");
                return;
            }

            String path = pathField.getText();
            if(path.length() > 100)
            {
                DialogUtil.showError(this, "Длина до картинки не может быть больше 100.");
                return;
            }

            ServiceEntity service = new ServiceEntity(
                    title, cost, duration, desc, discount, path
            );

            try {
                ServiceEntityManager.insert(service);
                DialogUtil.showInfo(this, "Запись успешно добавлена!");
                dispose();
                new ServiceTableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "Ошибка сохранения данных: " + ex.getMessage());
            }

        });
    }
}
