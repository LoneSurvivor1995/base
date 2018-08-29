package com.example.basespringboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiDatasourceController {

    @Value("${user.sex}")
    String[] sex;

    @GetMapping("/")
    public String get(){
        System.out.println(sex);
        return sex[1];
    }

}
