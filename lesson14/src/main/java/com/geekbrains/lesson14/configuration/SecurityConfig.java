package com.geekbrains.lesson14.configuration;

import com.geekbrains.lesson14.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

//конкретные настройки безопасности
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //защита отдельных методов (см. ProductService)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //стандартный бин, кот. отвечает за подключение к базе
    //его настройки вынесены в application.properties
    //private DataSource dataSource;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    //есть 2 варианта настройки источника данных

    //1) если мы хотим, чтобы данные брались из базы
    //для работы этого варианта необходимо, чтобы в pom.xml
    //был подключен spring-boot-starter-jdbc
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.jdbcAuthentication().dataSource(dataSource);
        auth.authenticationProvider(authenticationProvider());
    }

//    //2) если не хотим заморачиваться с БД
//    //делаем юзеров через ЮзерБилдер
//    //inMemoryAuthentication() - на сервере будут храниться набор пользователей
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser(users.username("user1").password("pass1").roles("USER", "ADMIN"))
//                .withUser(users.username("user2").password("pass2").roles("USER"));
//    }

    //прописываем правила доступа
    //здесь говорим какие запросы нужно обезопасить
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/products/show/**").hasAnyRole("ADMIN") //защищаем блок, кот. начинается с "/secured/**"
                .anyRequest().permitAll() //всем доступны любые запросы
                .and()
                //какая используется аутентификация на сайте
                //.httpBasic() //базовая аутентификация (старенькое окошко)
                .formLogin() //аутентификация через форму логина (стандартная форма спринга)
                //.loginPage("/login") //либо можно указать запрос на доступ к страничке с формой
                //.loginProcessingUrl("/authenticateTheUser") //куда форма пошлет пост-запрос с логином и паролем
                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //для получения деталей по пользователю используем собственный UserService
        auth.setPasswordEncoder(passwordEncoder()); //пароль будет лежать в БД в хэшированном виде, для хэширования используем BCrypt
        return auth;
    }


}
