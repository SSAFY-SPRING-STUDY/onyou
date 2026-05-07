package com.ssafy.ssafy_study;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private HelloService helloService;

    HelloController(HelloService helloService){
        this.helloService = helloService;
    }
    @GetMapping("/hello")
    public String hello(){
        return helloService.hi();
    }
}
