package com.example.testspring.Interface;

import com.example.testspring.Model.ProductRepository;
import org.springframework.util.MultiValueMap;

public interface PluginInterface {
    String getName();
    String getHtmlNavBar();
    String postRequest(MultiValueMap<String, String> formData, ProductRepository productRepository);
}
