package org.hoiboi.www.Entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
public class ProductEntity {
    private int id;
    private String title;
    private String type;
    private String desc;
    private String path;
    private double cost;

    private ImageIcon image;

    public ProductEntity(int id, String title, String type, String desc, String path, double cost) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.path = path;
        this.cost = cost;

        try {
            this.image = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource(path))
                    .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            try {
                this.image = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource("picture.png"))
                        .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            } catch (Exception ex) {
            }
        }
    }

    public ProductEntity(String title, String type, String desc, String path, double cost) {
        this.id = -1;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.path = path;
        this.cost = cost;
    }
}
