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
    @ManyToMany
    private Set<User> likers;

    public Product(String name, String code, String ean, String desc, Set<User> likers) {
        this.name = name;
        this.code = code;
        this.ean = ean;
        this.desc = desc;
        this.likers = likers;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<User> getLikers() {
        return likers;
    }
    public void addLiker(User user){
        this.likers.add(user);
    }
}
