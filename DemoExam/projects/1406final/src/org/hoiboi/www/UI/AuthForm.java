package org.hoiboi.www.UI;

import org.hoiboi.www.App;
import org.hoiboi.www.Manager.AuthManager;
import org.hoiboi.www.Util.BaseForm;
import org.hoiboi.www.Util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class AuthForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton guestButton;
    private JButton authButton;

    public AuthForm()
    {
        super(800, 400);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    private void initButtons()
    {
        authButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            try {
                String role = AuthManager.auth(login, password);
                if(role == null)
                {
                    DialogUtil.showError(this, "Неверный логин или пароль!");
                    return;
                }

                if("Администратор".equalsIgnoreCase(role))
                {
                    App.IS_ADMIN = true;
                }

                dispose();
                new ProductTableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        guestButton.addActionListener(e -> {
            App.IS_ADMIN = false;
            dispose();
            new ProductTableForm();
        });
    }
}
