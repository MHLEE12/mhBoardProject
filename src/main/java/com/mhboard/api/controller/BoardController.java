package com.mhboard.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    @GetMapping("/board")
    public String get() {
        return "Hello World!";
    }


}
