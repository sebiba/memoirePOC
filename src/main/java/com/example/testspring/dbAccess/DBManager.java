package com.example.testspring.dbAccess;

import com.example.testspring.Model.Product;
import com.example.testspring.Model.ProductRepository;
import com.example.testspring.Model.User;
import com.example.testspring.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DBManager {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Product> getAllProduct(){
        return new ArrayList<>((Collection<? extends Product>) productRepository.findAll());
    }
    public Product findProductByEan(String ean){
        return productRepository.findByEan(ean);
    }
    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public User findUserByName(String name){
        return userRepository.findByName(name);
    }
    public void addUser(String name) {
        this.userRepository.save(new User(name));
    }
}
