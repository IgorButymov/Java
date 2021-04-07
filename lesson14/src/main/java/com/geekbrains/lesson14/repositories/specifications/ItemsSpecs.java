package com.geekbrains.lesson14.repositories.specifications;

import com.geekbrains.lesson14.entities.Item;
import org.springframework.data.jpa.domain.Specification;

//класс, кот. хранит заранее подготовленные спецификации
public class ItemsSpecs {
    public static Specification<Item> titleContains(String word) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Item> costGreaterThanOrEq(int value) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), value);
        };
    }
}
