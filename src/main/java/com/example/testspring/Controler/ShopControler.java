package com.example.testspring.Controler;

import com.example.testspring.Model.Product;
import com.example.testspring.Model.User;
import com.example.testspring.dbAccess.dbManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class ShopControler {
    @Autowired
    dbManager dbManager ;

    @GetMapping("/bib")
    private String showProduct(Model model){
        List<Product> productList = dbManager.getAllProduct();
        Product detail = productList.iterator().next();
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
        return "bibliotheque";
    }

    @PostMapping("/bib/like")
    private String likeProduct(Model model, @RequestParam String ean, @RequestParam String name, RedirectAttributes redirAttrs) {
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
}
