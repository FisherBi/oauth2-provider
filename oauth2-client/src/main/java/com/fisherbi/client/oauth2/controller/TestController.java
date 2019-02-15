package com.fisherbi.client.oauth2.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class TestController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String index(){
        return "Hello World!";
    }
}
