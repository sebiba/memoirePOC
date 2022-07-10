package com.example.testspring.Plugin;

import com.example.testspring.Interface.PluginInterface;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Recherche implements PluginInterface {
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
    public Void postRequest(MultiValueMap<String, String> formData) {
        return null;
    }
}
