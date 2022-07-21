package com.example.testspring.Plugin;

import com.example.testspring.Interface.PluginInterface;
import com.example.testspring.Model.Product;
import com.example.testspring.Model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.auto.service.AutoService;

@AutoService(PluginInterface.class)
public class Recherche implements PluginInterface {
    private Product product;

    @Override
    public String getName() {
        return "Recherche";
    }
    @Override
    public String getHtmlNavBar(){
        Path filePath = Path.of("src/main/resources/static/rechercheNavBar.html");
        String content;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            content = "error loading navBarCode";
        }
        return content;
    }
    @Override
    public String postRequest(MultiValueMap<String, String> formData, ProductRepository productRepository) {
        List<Product> productList = (List<Product>) productRepository.findAll();
        for (Product product:productList) {
            for (Field field:product.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    String value = field.get(product).toString();
                    if(value.equals(formData.get("recherche").get(0))){
                        return "redirect:/bib/loadProduct/"+product.getEan();
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
