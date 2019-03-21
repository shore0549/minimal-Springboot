package com.wechat.bills.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {


    @GetMapping(value = {"/", "index.html"})
    public String index(){
        return "UserMap";
    }
}
