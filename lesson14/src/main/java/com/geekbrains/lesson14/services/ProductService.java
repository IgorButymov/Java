package com.geekbrains.lesson14.services;

import com.geekbrains.lesson14.entities.Product;
import com.geekbrains.lesson14.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
////    @Secured(value = "ADMIN") //защита на уровне метода
//    public List<Product> getAllProductsWithFilter(String word) {
//        List<Product> fullList = productRepository.findAll();
//        if (word == null) {
//            return fullList;
//        }
//        return fullList.stream().filter(p -> p.getTitle().contains(word)).collect(Collectors.toList());
//    }
//
//    public void add(Product product) {
//        productRepository.save(product);
//    }
//
//    public Product getById(Long id) {
//        return productRepository.findById(id);
//    }
//
//    public void deleteById(Long id) {
//        productRepository.removeById(id);
//    }
//
//    public void saveOrUpdate(Product product) {
//        productRepository.save(product);
//    }

    public Page<Product> getProductsWithPaginationAndFiltering(Specification<Product> specifications, Pageable pageable) {
        return productRepository.findAll(specifications, pageable);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }

//    //если метод должен быть выполнен в пределах одной транзакции (напр. при работе с БД)
//    @Transactional
//    public void doSomething() {
//
//    }
}
