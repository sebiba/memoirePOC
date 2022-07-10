package com.example.testspring.Interface;

import org.springframework.util.MultiValueMap;

public interface PluginInterface {
    String getName();
    String getHtmlNavBar();
    Void postRequest(MultiValueMap<String, String> formData);
}
