package com.example.testspring.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "ean",nullable = false, length = 14)
    private String ean;
    @Column(name = "name",nullable = false, length = 50)
    private String name;
    @Column(name = "code",nullable = false, length = 10)
    private String code;
    @Column(name = "description")
    private String desc;

    public Product(String name, String code, String ean, String desc) {
        this.name = name;
        this.code = code;
        this.ean = ean;
        this.desc = desc;
    }

    public Product() {

    }
//region get/set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getEan() {
        return ean;
    }

    public String getDesc() {
        return desc;
    }

//endregion
}
