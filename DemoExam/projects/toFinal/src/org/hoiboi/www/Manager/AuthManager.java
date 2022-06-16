package org.hoiboi.www.Manager;

import org.hoiboi.www.App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {
    public static String auth(String login, String password) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "SELECT * FROM users WHERE login=? AND password=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getString("role");
            }
            return null;

        }
    }
}
