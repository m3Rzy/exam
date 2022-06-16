package org.hoiboi.www.UI;

import org.hoiboi.www.App;
import org.hoiboi.www.Entity.ClientEntity;
import org.hoiboi.www.Manager.ClientManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class ClientEditForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField fioField;
    private JTextField phoneField;
    private JComboBox genderBox;
    private JTextField birthdayField;
    private JTextField cashField;
    private JTextField pathField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton backButton;
    private ClientEntity client;

    public ClientEditForm(ClientEntity client)
    {
        super(1200, 800);
        this.client = client;
        setContentPane(mainPanel);
        initBox();
        initFields();
        initButtons();

        setVisible(true);
    }

    private void initBox()
    {
        genderBox.addItem("Мужской");
        genderBox.addItem("Женский");
    }

    private void initFields()
    {
        fioField.setText(client.getFio());
        phoneField.setText(client.getPhone());
        genderBox.setSelectedIndex(client.getGender() == 'м' ? 0 : 1);
        birthdayField.setText(App.DATE_FORMAT.format(client.getBirthday()));
        cashField.setText(String.valueOf(client.getCash()));
        pathField.setText(client.getImagepath());
    }

    private void initButtons()
    {
        backButton.addActionListener(e -> {
            dispose();
            new ClientTableForm();
        });

        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Вы точно хотите удалить клиента?",
                    "Удаление клиента",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                try {
                    ClientManager.delete(client);
                    DialogUtil.showInfo(this, "Клиент был успешно удалён!");
                    dispose();
                    new ClientTableForm();
                } catch (SQLException ex) {
                    DialogUtil.showError(this, "Ошибка удаления клиента: " + ex.getMessage());
                }
            }
        });

        saveButton.addActionListener(e -> {
            String fio = fioField.getText();
            if(fio.isEmpty() || fio.length() > 255)
            {
                DialogUtil.showError(this, "ФИО не может быть пустым или больше 255 символов!");
                return;
            }

            String phone = phoneField.getText();
            if(phone.isEmpty() || phone.length() > 255)
            {
                DialogUtil.showError(this, "Номер телефона не может быть пустым или больше 255 символов!");
                return;
            }

            char gender = genderBox.getSelectedIndex() == 0 ? 'м' : 'ж';

            Date birthday = null;
            try {
                birthday = App.DATE_FORMAT.parse(birthdayField.getText());
            } catch (ParseException ex) {
                DialogUtil.showError(this, "Неправильный формат даты рождения!" +
                        "\n Верный формат: 'год-месяц-день'");
                return;
            }

            double cash = -1;
            cash = Double.parseDouble(cashField.getText());
            if(cash <= -1)
            {
                DialogUtil.showError(this, "Сумма не может быть меньше 0.");
                return;
            }

            String path = pathField.getText();

            client.setFio(fio);
            client.setPhone(phone);
            client.setGender(gender);
            client.setBirthday(birthday);
            client.setCash(cash);
            client.setImagepath(path);

            try {
                ClientManager.update(client);
                DialogUtil.showInfo(this, "Клиент был успешно изменён!");
                dispose();
                new ClientTableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка изменения клиента: " + ex.getMessage());
            }
        });
    }
}
