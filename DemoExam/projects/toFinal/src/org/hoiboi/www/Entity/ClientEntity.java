package org.hoiboi.www.Entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

@Data
public class ClientEntity {
    private int id;
    private String fio;
    private String phone;
    private char gender;
    private Date regdate;
    private Date birthday;
    private double cash;
    private String Imagepath;

    private ImageIcon image;

    public ClientEntity(int id, String fio, String phone, char gender, Date regdate, Date birthday, double cash, String imagepath) {
        this.id = id;
        this.fio = fio;
        this.phone = phone;
        this.gender = gender;
        this.regdate = regdate;
        this.birthday = birthday;
        this.cash = cash;
        Imagepath = imagepath;

        try {
            this.image = new ImageIcon(ImageIO.read(ClientEntity.class.getClassLoader().getResource(imagepath))
                    .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {

        }
    }

    public ClientEntity(String fio, String phone, char gender, Date regdate, Date birthday, double cash, String imagepath) {
        this.id = -1;
        this.fio = fio;
        this.phone = phone;
        this.gender = gender;
        this.regdate = regdate;
        this.birthday = birthday;
        this.cash = cash;
        Imagepath = imagepath;
    }
}
