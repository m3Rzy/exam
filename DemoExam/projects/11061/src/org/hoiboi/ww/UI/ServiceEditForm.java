package org.hoiboi.ww.UI;

import org.hoiboi.ww.Entity.ServiceEntity;
import org.hoiboi.ww.Manager.ServiceManager;
import org.hoiboi.ww.Util.BaseForm;
import org.hoiboi.ww.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ServiceEditForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField titleField;
    private JTextField costField;
    private JTextField durationField;
    private JTextField descField;
    private JTextField discountField;
    private JTextField pathField;
    private JButton deleteButton;
    private JButton backButton;
    private JButton editButton;
    private ServiceEntity service;

    public ServiceEditForm(ServiceEntity service)
    {
        super(1200, 800);
        this.service = service;

        setContentPane(mainPanel);
        initFields();
        initButtons();
        setVisible(true);
    }

    private void initFields()
    {
        idField.setEditable(false);
        idField.setText(String.valueOf(service.getId()));
        titleField.setText(service.getTitle());
        costField.setText(String.valueOf(service.getCost()));
        durationField.setText(String.valueOf(service.getDuration()));
        descField.setText(service.getDesc());
        discountField.setText(String.valueOf(service.getDiscount()));
        pathField.setText(service.getPath());
    }

    private void initButtons()
    {
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });

        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Вы точно хотите удалить сервис?",
                    "Удаление сервиса",
                    JOptionPane.YES_NO_OPTION
                    ) == JOptionPane.YES_OPTION) {
                try {
                    ServiceManager.delete(service);
                    DialogUtil.showInfo(this, "Сервис был успешно удалён!");
                    dispose();
                    new ServiceTableForm();
                } catch (SQLException ex) {
                    DialogUtil.showError(this, "Ошибка удаления сервиса: " + ex.getMessage());
                }
            }
        });

        editButton.addActionListener(e -> {
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

            service.setTitle(title);
            service.setCost(cost);
            service.setDuration(duration);
            service.setDesc(desc);
            service.setDiscount(discount);
            service.setPath(path);

            try {
                ServiceManager.update(service);
                DialogUtil.showInfo(this, "Сервис был успешно изменён!");
                dispose();
                new ServiceTableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка изменения сервиса: " + ex.getMessage());
            }
        });
    }
}
