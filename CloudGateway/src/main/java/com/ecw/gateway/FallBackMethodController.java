package com.ecw.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/adminServiceFallBack")
    public String adminServiceFallBackMethod(){
        return  "Admin Service is taking longer then Expected." +
                " Please try again later";
    }
    @GetMapping("/webServiceFallBack")
    public String webmentServiceFallBackMethod(){
        return  "Web Service is taking longer then Expected." +
                " Please try again later";
    }
}
