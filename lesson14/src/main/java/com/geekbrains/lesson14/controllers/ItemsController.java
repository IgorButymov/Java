package com.geekbrains.lesson14.controllers;

import com.geekbrains.lesson14.entities.Item;
import com.geekbrains.lesson14.repositories.specifications.ItemsSpecs;
import com.geekbrains.lesson14.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private ItemsService itemsService;

    @Autowired
    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

//    //1 часть урока
//    @GetMapping
//    public String showItemsPage(Model model) {
//        model.addAttribute("items", itemsService.getAllItems());
//        return "items";
//    }

    //2 часть урока
    @GetMapping
    public String showItemsPage(Model model) {
        //пустая спецификация, ничего не будет отсеивать (findAll())
        Specification<Item> filter = Specification.where(null);

        //добавляем спецификацию, в кот. стоимость товара >= 30
        filter = filter.and(ItemsSpecs.costGreaterThanOrEq(30));

        //используем этот ^ фильтр и говорим, что хотим видеть нулевую страницу при разбивке страниц по два элемента
        List<Item> resultList = itemsService.getItemsWithPaginationAndFiltering(filter, PageRequest.of(0, 2)).getContent();
        model.addAttribute("items", resultList);
        return "items";
    }

}
