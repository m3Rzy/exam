package org.hoiboi.www.Manager;

import org.hoiboi.www.App;
import org.hoiboi.www.Entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static void insert(ProductEntity product) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "INSERT INTO product(Title, ProductType, Description, Image, Cost) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getType());
            ps.setString(3, product.getDesc());
            ps.setString(4, product.getPath());
            ps.setDouble(5, product.getCost());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next())
            {
                product.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("Ошибка добавления продукта!");
        }
    }

    public static void update(ProductEntity product) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "UPDATE product SET Title=?, ProductType=?, Description=?, Image=?, Cost=? WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getType());
            ps.setString(3, product.getDesc());
            ps.setString(4, product.getPath());
            ps.setDouble(5, product.getCost());
            ps.setInt(6, product.getId());

            ps.executeUpdate();

        }
    }

    public static List<ProductEntity> selectAll() throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "SELECT * FROM product";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ProductEntity> list = new ArrayList<>();
            while(resultSet.next())
            {
                list.add(new ProductEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("ProductType"),
                        resultSet.getString("Description"),
                        resultSet.getString("Image"),
                        resultSet.getDouble("Cost")
                ));
            }
            return list;
        }
    }

    public static void delete(ProductEntity product) throws SQLException
    {
        try(Connection c = App.getConnection())
        {
            String sql = "DELETE FROM product WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, product.getId());
            ps.executeUpdate();
        }
    }
}
