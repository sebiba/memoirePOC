package com.example.testspring.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="liker")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @ManyToMany
    private Set<Product> likedProduct;

    public User(String name) {
        this.name = name;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
