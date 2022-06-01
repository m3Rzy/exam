package org.hoiboi;

import org.hoiboi.UI.ServiceTableForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ServiceTableForm();
//        поменять слэш на противоположный у путей до картинок, скинуть картинки сервисов в resources, сделать crud + sort
    }

    public static Connection getConnection() throws SQLException {
//        БД .22 /demoexam/service - @database-1
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/demoexam", "root", "123321");
    }
}
