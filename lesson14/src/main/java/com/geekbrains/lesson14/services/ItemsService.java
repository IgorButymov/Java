package com.geekbrains.lesson14.services;

import com.geekbrains.lesson14.entities.Item;
import com.geekbrains.lesson14.entities.ItemProjection;
import com.geekbrains.lesson14.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ItemsService {
    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

//    //1 часть урока
//    public List<Item> getAllItems() {
//        System.out.println(itemRepository.findByTitle("Tree"));
//        System.out.println(itemRepository.findByCostBetween(30, 70));
//        /* примеры для работы с пагинацией
//        //5 - какая страница нам нужна
//        //20 - размер страницы
//        itemRepository.findAll(PageRequest.of(5, 20));
//        */
//        return itemRepository.findAll();
//    }

    //2 часть урока
    //Page содержит информацию о:
    //кол-ве страниц, кол-ве объектов, номере текущей страницы,
    // список объектов на текущей странице
    public Page<Item> getItemsWithPaginationAndFiltering(Specification<Item> specifications, Pageable pageable) {

        return itemRepository.findAll(specifications, pageable);
    }
}
