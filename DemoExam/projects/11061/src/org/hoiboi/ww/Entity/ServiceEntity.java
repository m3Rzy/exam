package org.hoiboi.ww.Entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

@Data
public class ServiceEntity {
    private int id;
    private String title;
    private double cost;
    private int duration;
    private String desc;
    private int discount;
    private String path;

    private ImageIcon image;

    public ServiceEntity(int id, String title, double cost, int duration, String desc, int discount, String path) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.desc = desc;
        this.discount = discount;
        this.path = path;

        try {
            this.image = new ImageIcon(ImageIO.read(ServiceEntity.class.getClassLoader().getResource(path))
                    .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
        }
    }

    public ServiceEntity(String title, double cost, int duration, String desc, int discount, String path) {
        this.id = -1;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.desc = desc;
        this.discount = discount;
        this.path = path;
    }
}
