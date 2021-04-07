package com.geekbrains.lesson14.repositories;

import com.geekbrains.lesson14.entities.Item;
import com.geekbrains.lesson14.entities.ItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

////первая часть урока
//@Repository
////JpaRepository - интерфейс, в кот. большинство методов уже готово
////это от spring-data
//public interface ItemRepository extends JpaRepository<Item, Long> {
//    //интерфейс, поддерживающий пагинацию и сортировку
//    //                          extends PagingAndSortingRepository<Item, Long>
//    //интерфейс, поддерживающий фильтры
//    //                          extends JpaSpecificationExecutor<...>
//
//
//
//
//    //спец. запрос
//    //spring распарсит это название, сам сформирует запрос к БД, найдет и вернет объект типа Item
//    //SELECT i FROM Item i WHERE i.title = 1?
//    Item findByTitle(String title);
//
//    //спец. запрос
//    //spring распарсит это название, сам сформирует запрос к БД, найдет и вернет объект типа Item
//    List<Item> findByCostBetween(int min, int max);
//
//    //найти по цене и отсортировать по убыванию
//    //List<Item> findByCostOrderByTitleDesc(int cost);
//
////    //собственный запрос (JPQL)
////    @Query(value = "")
////    List<Item> myMethodName1();
////
////    //nativeQuery = true - пишем запрос на SQL
////    @Query(value = "", nativeQuery = true)
////    List<Item> myMethodName2();
//
//    //найти предметы, где title заканчивает на подстроку (JPQL)
//    //% - любая подстрока
//    //1 - первый аргумент метода
//    @Query(value = "SELECT i FROM Item i WHERE i.title LIKE %?1")
//    List<Item> findByTitleEndWith(String substr);
//}

//вторая часть урока
@Repository
//PagingAndSortingRepository - пагинация + сортировка
//JpaSpecificationExecutor - спецификации
public interface ItemRepository extends PagingAndSortingRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}
