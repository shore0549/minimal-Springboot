package com.wechat.bills.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname PagesController
 * @Description TODO
 * @Date 2019/3/21 17:29
 * @Created by elephone
 */
@Controller
public class PagesController {



    @GetMapping(value = {"/", "index.html"})
    /**
     *功能描述
     * @author elephone
     * @date 2019/3/21
     * @param  * @param
     * @return java.lang.String
     */
    public String index(){
        return "UserMap";
    }
}
