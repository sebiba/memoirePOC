package com.example.testspring.Controler;

import com.example.testspring.Interface.PluginInterface;
import com.example.testspring.Model.Product;
import com.example.testspring.Model.User;
import com.example.testspring.dbAccess.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ShopControler {
    @Autowired
    DBManager dbManager;
    Map<String, PluginInterface> plugins;

    @GetMapping("/bib")
    private String showProduct(Model model) {
        List<Product> productList = dbManager.getAllProduct();
        Product detail = productList.iterator().next();
        List<String> htmlCodePlugin = new ArrayList<>();
        this.plugins = MainControler.getInstance().loadPlugins();
        this.plugins.values().forEach(x->htmlCodePlugin.add(x.getHtmlNavBar()));
        model.addAttribute("plugins",htmlCodePlugin);
        model.addAttribute("productList",productList);
        model.addAttribute("productDetail",detail);
        model.addAttribute("productLike",detail);
        return "bibliotheque";
    }

    @GetMapping("/bib/loadProduct/{ean}")
    private String loadProduct(@PathVariable("ean") String ean, Model model){
        List<Product> test = dbManager.getAllProduct();
        model.addAttribute("productList",test);
        Product detail = test.stream().filter(x->ean.equals(x.getEan())).findFirst().orElse(null);
        model.addAttribute("productDetail",detail);
        model.addAttribute("productLike",new Product());
        List<String> htmlCodePlugin = new ArrayList<>();
        this.plugins = MainControler.getInstance().loadPlugins();
        this.plugins.values().forEach(x->htmlCodePlugin.add(x.getHtmlNavBar()));
        model.addAttribute("plugins",htmlCodePlugin);
        return "bibliotheque";
    }

    @PostMapping("/bib/like")
    private String likeProduct(@RequestParam String ean, @RequestParam String name, RedirectAttributes redirAttrs) {
        Product produit = dbManager.findProductByEan(ean);
        User user = dbManager.findUserByName(name);
        //create new user
        if (user == null) {
            dbManager.addUser(name);
            user = dbManager.findUserByName(name);
        } else {
            if(produit.getLikers().contains(user)){
                redirAttrs.addFlashAttribute("error", "Vous ne pouvez pas liker 2 fois le même produits");
                return "redirect:/bib";
            }
        }
        produit.addLiker(user);
        dbManager.getProductRepository().save(produit);
        return "redirect:/bib";
    }

    @RequestMapping(value = "/bib/test/{pluginName}",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    private String pluginsRequest(@PathVariable("pluginName")String pluginName, @RequestBody MultiValueMap<String, String> formData){
        if(this.plugins.containsKey(pluginName)){
            return this.plugins.get(pluginName).postRequest(formData, this.dbManager.getProductRepository());
        }
        return "redirect:/bib";
    }
}
