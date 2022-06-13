package org.hoiboi.www.Manager;

import org.hoiboi.www.App;
import org.hoiboi.www.Entity.ServiceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceManager {
    public static void insert(ServiceEntity service) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "INSERT INTO service(Title, Cost, Duration, Description, Discount, MainImagePath) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDesc());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getPath());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next())
            {
                service.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("ОШИБКА");
        }
    }

    public static void update(ServiceEntity service) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "UPDATE service SET Title=?, Cost=?, Duration=?, Description=?, Discount=?, MainImagePath=? WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDesc());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getPath());
            ps.setInt(7, service.getId());
            ps.executeUpdate();

        }
    }
}
