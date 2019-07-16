package com.github.makiftutuncu.scrabbleapi.controllers;

import com.github.makiftutuncu.scrabbleapi.services.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private HomeService service;

    @Autowired
    public HomeController(HomeService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    void index() {
        service.greet();
    }
}
