package com.example.testspring.Controler;

import com.example.testspring.Interface.PluginInterface;
import com.example.testspring.PluginLoader;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

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
        PluginLoader pluginLoader = new PluginLoader();
        Map<String, PluginInterface>pluginsList = new HashMap<>();
        try {
            for (PluginInterface plugin:pluginLoader.loadAllPlugins()) {
                pluginsList.put(plugin.getName(),plugin);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pluginsList;
    }
}
