package com.mhboard.api.controller;

import com.mhboard.api.request.BoardWrite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class BoardController {

    // Http Method
    // GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD, TRACE, CONNECT

//    @GetMapping("/board")
//    public String get() {
//        return "Hello World!";
//    }

    @PostMapping("/board")
    public String board(@ModelAttribute BoardWrite params) {
        //@ModelAttribute 생략가능
        log.info("params={}", params.toString());
        return "Hello World!";
    }

}
