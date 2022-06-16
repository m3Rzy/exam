package org.hoiboi.www.Manager;

import org.hoiboi.www.App;
import org.hoiboi.www.Entity.ClientEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    public static void insert(ClientEntity client) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "INSERT INTO client(fio, phone, gender, regdate, birthday, cash, image) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getFio());
            ps.setString(2, client.getPhone());
            ps.setString(3, String.valueOf(client.getGender()));
            ps.setTimestamp(4, new Timestamp(client.getRegdate().getTime()));
            ps.setTimestamp(5, new Timestamp(client.getBirthday().getTime()));
            ps.setDouble(6, client.getCash());
            ps.setString(7, client.getImagepath());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next())
            {
                client.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("Ошибка добавления клиента");
        }
    }

    public static void update(ClientEntity client) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "UPDATE client SET fio=?, phone=?, gender=?, regdate=?, birthday=?, cash=?, image=? WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, client.getFio());
            ps.setString(2, client.getPhone());
            ps.setString(3, String.valueOf(client.getGender()));
            ps.setTimestamp(4, new Timestamp(client.getRegdate().getTime()));
            ps.setTimestamp(5, new Timestamp(client.getBirthday().getTime()));
            ps.setDouble(6, client.getCash());
            ps.setString(7, client.getImagepath());
            ps.setInt(8, client.getId());

            ps.executeUpdate();

        }
    }

    public static List<ClientEntity> selectAll() throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "SELECT * FROM client";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ClientEntity> list = new ArrayList<>();
            while(resultSet.next())
            {
                list.add(new ClientEntity(
                   resultSet.getInt("id"),
                   resultSet.getString("fio"),
                   resultSet.getString("phone"),
                   resultSet.getString("gender").charAt(0),
                   resultSet.getTimestamp("regdate"),
                        resultSet.getTimestamp("birthday"),
                        resultSet.getDouble("cash"),
                        resultSet.getString("image")
                ));
            }
            return list;
        }
    }

    public static void delete(ClientEntity client) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "DELETE FROM client WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, client.getId());
            ps.executeUpdate();
        }
    }
}
