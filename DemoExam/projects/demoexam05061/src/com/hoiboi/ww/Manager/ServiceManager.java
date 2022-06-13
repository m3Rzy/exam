package com.hoiboi.ww.Manager;

import com.hoiboi.ww.App;
import com.hoiboi.ww.Entity.ServiceEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    public static void insert(ServiceEntity service) throws SQLException
    {
        try (Connection c = App.getConnection())
        {
            String sql = "INSERT INTO service(Title, Cost, Duration, Description, Discount, MainImagePath) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDesc());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getMainImagePath());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next())
            {
                service.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("Ошибка добалвения сервиса!");
        }
    }

    public static void update(ServiceEntity service) throws SQLException
    {
        try (Connection c = App.getConnection())
        {
            String sql = "UPDATE service SET Title=?, Cost=?, Duration=?, Description=?, Discount=?, MainImagePath=? WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDesc());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getMainImagePath());
            ps.setInt(7, service.getId());
            ps.executeUpdate();

        }
    }

    public static List<ServiceEntity> selectAll() throws SQLException
    {
        try (Connection c = App.getConnection())
        {
            String sql = "SELECT * FROM service";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ServiceEntity> list = new ArrayList<>();
            while(resultSet.next())
            {
                list.add(new ServiceEntity(
                   resultSet.getInt("ID"),
                   resultSet.getString("Title"),
                   resultSet.getDouble("Cost"),
                   resultSet.getInt("Duration"),
                   resultSet.getString("Description"),
                   resultSet.getInt("Discount"),
                   resultSet.getString("MainImagePath")
                ));
            }
            return list;
        }
    }

    public static void delete(ServiceEntity service) throws SQLException
    {
        try (Connection c = App.getConnection())
        {
            String sql = "DELETE FROM service WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, service.getId());
            ps.executeUpdate();
        }
    }
}
