package com.geekbrains.lesson14.repositories.specifications;

import com.geekbrains.lesson14.entities.Item;
import com.geekbrains.lesson14.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductsSpecs {
    public static Specification<Product> titleContains(String word) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Product> priceGreaterThanOrEq(BigDecimal value) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
        };
    }

    public static Specification<Product> priceLesserThanOrEq(BigDecimal value) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
        };
    }
}
