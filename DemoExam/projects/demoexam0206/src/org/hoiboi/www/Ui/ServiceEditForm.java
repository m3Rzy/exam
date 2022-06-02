package org.hoiboi.www.Ui;

import org.hoiboi.www.Entity.ServiceEntity;
import org.hoiboi.www.Manager.ServiceEntityManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ServiceEditForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField costField;
    private JTextField durationField;
    private JTextField descField;
    private JTextField discountField;
    private JTextField pathField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTextField idField;
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

    public void initFields()
    {
        idField.setEditable(false);
        idField.setText(String.valueOf(service.getId()));
        titleField.setText(service.getTitle());
        costField.setText(String.valueOf(service.getCost()));
        durationField.setText(String.valueOf(service.getDuration()));
        descField.setText(service.getDesc());
        discountField.setText(String.valueOf(service.getDiscount()));
        pathField.setText(service.getMainImagePath());
    }

    public void initButtons()
    {
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            if(title.isEmpty() || title.length() > 50)
            {
                DialogUtil.showError(this, "Длина символов либо 0, либо превышает 50 символов.");
                return;
            }
            double cost = -1;
            cost = Double.parseDouble(costField.getText());
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
            int discount = -1;
            discount = Integer.parseInt(discountField.getText());
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

            service.setTitle(title);
            service.setCost(cost);
            service.setDuration(duration);
            service.setDesc(desc);
            service.setDiscount(discount);
            service.setMainImagePath(path);

            try {
                ServiceEntityManager.update(service);
                DialogUtil.showInfo(this, "Запись успешно отредактирована");
                dispose();
                new ServiceTableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "Ошибка сохранения данных: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(
                    this,
                    "Вы точно хотите удалить сервис?",
                    "Да!",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                try {
                    ServiceEntityManager.delete(service);
                    DialogUtil.showInfo(this, "Сущность была успешно удалена!");
                    dispose();
                    new ServiceTableForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    DialogUtil.showError(this, "Ошибка удаления сервиса: " + ex.getMessage());
                }

            }
        });
    }
}
