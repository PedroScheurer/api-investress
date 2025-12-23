package com.pedroscheurer.investress.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class HelloWordController {

    @GetMapping
    public String index() {
        return "Bem-vindo à API";
    }
}
