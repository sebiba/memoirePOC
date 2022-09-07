package com.example.testspring.dbAccess;

import com.example.testspring.Model.Product;
import com.example.testspring.Model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DBManager {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        return new ArrayList<>((Collection<? extends Product>) productRepository.findAll());
    }
    public Product findProductByEan(String ean){
        return productRepository.findByEan(ean);
    }
    public ProductRepository getProductRepository() {
        return productRepository;
    }
}
