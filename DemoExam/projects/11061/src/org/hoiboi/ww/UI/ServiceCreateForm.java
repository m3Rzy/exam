package org.hoiboi.ww.UI;

import org.hoiboi.ww.Entity.ServiceEntity;
import org.hoiboi.ww.Manager.ServiceManager;
import org.hoiboi.ww.Util.BaseForm;
import org.hoiboi.ww.Util.DialogUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ServiceCreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField costField;
    private JTextField durationField;
    private JTextField descField;
    private JTextField discountField;
    private JTextField pathField;
    private JButton saveButton;
    private JButton backButton;

    public ServiceCreateForm()
    {
        super(1200, 800);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    private void initButtons()
    {
        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            if(title.isEmpty() || title.length() > 255)
            {
                DialogUtil.showError(this, "Название не может быть пустым или больше 255 символов.");
                return;
            }

            double cost = Double.parseDouble(costField.getText());
            if(cost <= 0)
            {
                DialogUtil.showError(this, "Цена не может быть пустой или меньше 0.");
                return;
            }

            int duration = Integer.parseInt(durationField.getText());
            if(durationField == null || duration < 0)
            {
                DialogUtil.showError(this, "Длительность не может быть пустой или меньше 0.");
                return;
            }

            String desc = descField.getText();

            int discount = Integer.parseInt(discountField.getText());
            if(discountField == null || discount > 100 || discount < 0)
            {
                DialogUtil.showError(this, "Скидка не может быть пустой, больше 100 или меньше 0.");
                return;
            }

            String path = pathField.getText();

            ServiceEntity service = new ServiceEntity(
                    title, cost, duration, desc, discount, path
            );

            try {
                ServiceManager.insert(service);
                DialogUtil.showInfo(this, "Сервис был успешно добавлен!");
                dispose();
                new ServiceTableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка добавления сервиса: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });
    }
}
