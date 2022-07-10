package com.example.testspring.Controler;

import com.example.testspring.Interface.PluginInterface;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@Controller
public class MainControler {
    private static MainControler instance = null;
    public static MainControler getInstance(){
        if (instance == null){
            instance = new MainControler();
        }
        return instance;
    }

    /**
     * Load plugin available for prossessing
     * @return Map<String, interpreter> plugin's name and the plugin
     */
    public Map<String, PluginInterface> loadPlugins(){
        ServiceLoader<PluginInterface> serviceLoader = ServiceLoader.load(PluginInterface.class);
        Map<String, PluginInterface> services = new HashMap<>();
        for (PluginInterface service : serviceLoader) {
            services.put(service.getName(), service);
        }
        return services;
    }
}
