package com.fisherbi.oauth2.provider.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fisbii on 18-8-2.
 */

@RestController
@EnableAutoConfiguration
public class TestController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String index(){
        return "Hello World!";
    }
}
