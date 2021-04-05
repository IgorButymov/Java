package com.geekbrains.lesson14.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller

public class MainController {

    @GetMapping("/index")
    public String doSomething() {
        return "index";
    }

    @GetMapping("/hello/{name}")
    //реагирует только на Get запросы
    //@PathVariable - переменная name зашита в пути, а не через параметры "?"
    public String helloRequest(Model model, @PathVariable(value = "name") String name) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/form")
    public String showForm() {
        return "simple-form";
    }

    //форма при submit посылает post запрос на адрес /form
    //и мы выдернем оттуда @RequestParam name и @RequestParam email
    @PostMapping("/form")
    public String saveForm(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email) {
        System.out.println(name);
        System.out.println(email);
        //после всех действий перенаправляем пользователя на /index
        return "redirect:/index";
    }

    @GetMapping("/addcat")
    public String showAddCatForm(Model model) {
        Cat cat = new Cat(1L, "Barsik", "White");
        //Cat cat = new Cat(1L, null, null);
        model.addAttribute("cat", cat);
        return "cat-form";
    }

    @PostMapping("/addcat")
    //@ModelAttribute - ожидаем получить объект (кота)
    public String showAddCatForm(@ModelAttribute(value = "cat") Cat cat) {
        System.out.println(cat.getId() + " " + cat.getName() + " " + cat.getColor());
        return "redirect:/index";
    }

//    @GetMapping("/hello")
//    //параметры: закидываем ссылки на объекты каких-либо типов (Model, HttpSession ...)
//    //и Spring будет их сюда инжектить автоматически
//    //Model - можем прокидывать данные с бэкэнда в html
//    //@RequestParam - обязательно ожидаем какой-то параметр (в данном случаем name)
//    //если пишем в параметрах @RequestParam "required = false", то это будет не обязательным параметром
//    //.../demo/hello?name=Bob  //? - указыаем параметры
//    public String helloRequest(Model model, @RequestParam(value = "name") String name) {
//        model.addAttribute("name", name);
//        return "hello";
//    }

//    @GetMapping("/hello")
//    @ResponseBody //ответом будет объект, а не страница (строка hello в данном случае)
//    public String helloRequest() {
//        return "hello";
//    }

//    @GetMapping("/hello")
//    @ResponseBody //ответом будет объект, а не страница (строка hello в данном случае)
//    //объект будет в JSON формате
//    public Cat helloRequest() {
//        return new Cat(1L, "Barsik");
//    }
}
