package com.geekbrains.lesson14.controllers;

import com.geekbrains.lesson14.entities.Product;
import com.geekbrains.lesson14.entities.User;
import com.geekbrains.lesson14.repositories.UserRepository;
import com.geekbrains.lesson14.repositories.specifications.ProductsSpecs;
import com.geekbrains.lesson14.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductService productService;

    //фиговый код, контроллер должен лезть в сервис, а не в репозиторий
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    //если мы хотим сюда запихнуть информацию о пользователе пишем в параметре Principal principal - это информация о пользователе
    public String showProductsList(Principal principal, Model model,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                   @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice
    ) {
        if (principal != null) {
            User user = userRepository.findOneByUsername(principal.getName());
            System.out.println(user.getEmail());
        }
        if (page == null) {
            page = 1;
        }
        Specification<Product> specification = Specification.where(null);
        if (word != null) {
            specification = specification.and(ProductsSpecs.titleContains(word));
        }
        if (minPrice != null) {
            specification = specification.and(ProductsSpecs.priceGreaterThanOrEq(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductsSpecs.priceLesserThanOrEq(maxPrice));
        }
        Product product = new Product();
        model.addAttribute("products", productService.getProductsWithPaginationAndFiltering(specification, PageRequest.of(page - 1, 5)).getContent());
        model.addAttribute("product", product);
        model.addAttribute("word", word);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product-edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(Model model, @PathVariable(value = "id") Long id) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @PostMapping("/edit")
    public String addProduct(@ModelAttribute(value = "product") Product product) {
        productService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product-page";
    }

    @GetMapping("/delete/{id}")
    public String deleteOneProduct(@PathVariable(value = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

}
