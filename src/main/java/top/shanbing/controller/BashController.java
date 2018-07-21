package top.shanbing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class BashController {
    @RequestMapping("/")
    public String index(){
        System.out.println(LocalDateTime.now());
        return "index";
    }
}
