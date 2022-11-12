package com.mhboard.api.controller;

import com.mhboard.api.request.BoardWrite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BoardController {
    
    @PostMapping("/board")
    public Map<String, String> board(@RequestBody @Valid BoardWrite params) {


        return Map.of();
    }

}
