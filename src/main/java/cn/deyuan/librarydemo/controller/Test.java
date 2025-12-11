package cn.deyuan.librarydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: admin
 * date: 2025/12/11 13:31
 */
@RestController
public class Test {

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return "1234567890";
    }
}
