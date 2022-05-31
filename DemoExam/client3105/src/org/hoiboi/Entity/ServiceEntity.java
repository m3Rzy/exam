package org.hoiboi.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServiceEntity {
    private int id;
    private String title;
    private double cost;
    private int duration;
    private String description;
    private int discount;
    private String MainImagePath;
}
