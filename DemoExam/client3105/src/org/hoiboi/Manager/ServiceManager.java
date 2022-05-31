package org.hoiboi.Manager;

import org.hoiboi.App;
import org.hoiboi.Entity.ServiceEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    public static void insert(ServiceEntity service) throws SQLException {
        try(Connection c = App.getConnection())
        {
            String sql = "INSERT INTO Service(Title, Cost, Duration, Discount, Description, MainImagePath) VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDescription());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getMainImagePath());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                service.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("Не удалось добавить сущность.");
        }
    }

    public static void update(ServiceEntity service) throws SQLException {
        try(Connection c = App.getConnection())
        {
            String sql = "UPDATE Service SET Title=?, Cost=?, Duration=?, Discount=?, Description=?, MainImagePath=? WHERE ID=?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDescription());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getMainImagePath());

            ps.executeUpdate();

        }
    }

    public static List<ServiceEntity> selectAll() throws SQLException {
        try (Connection c = App.getConnection())
        {
            String sql = "SELECT * FROM Service";

            Statement s = c.createStatement();
            ResultSet resultset = s.executeQuery(sql);

            List<ServiceEntity> list = new ArrayList<>();
            while(resultset.next())
            {
                list.add(new ServiceEntity(
                        resultset.getInt("ID"),
                        resultset.getString("Title"),
                        resultset.getDouble("Cost"),
                        resultset.getInt("Duration"),
                        resultset.getString("Description"),
                        resultset.getInt("Discount"),
                        resultset.getString("MainImagePath")
                ));
            }
            return list;
        }
    }

    public static void delete(ServiceEntity service) throws SQLException
    {
        try (Connection c = App.getConnection())
        {
            String sql = "DELETE FROM Service WHERE ID=?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, service.getId());
            ps.executeUpdate();
        }
    }
}
