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
    private String MainImagePath;

    private ImageIcon image;

    public ServiceEntity(int id, String title, double cost, int duration, String desc, int discount, String mainImagePath) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.desc = desc;
        this.discount = discount;
        MainImagePath = mainImagePath;
        try {
            this.image = new ImageIcon(ImageIO.read(ServiceEntity.class.getClassLoader().getResource(MainImagePath))
                    .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServiceEntity(String title, double cost, int duration, String desc, int discount, String mainImagePath) {
        this.id = -1;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.desc = desc;
        this.discount = discount;
        MainImagePath = mainImagePath;
    }
}
