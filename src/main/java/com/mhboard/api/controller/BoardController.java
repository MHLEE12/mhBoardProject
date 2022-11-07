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

//    @GetMapping("/board")
//    public String get() {
//        return "Hello World!";
//    }

    @PostMapping("/board")
    public Map<String, String> board(@RequestBody @Valid BoardWrite params, BindingResult result) {
//        log.info("params={}", params.toString());
        if(result.hasErrors()) {
            // 에러 메세지를 json 형태로!
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메세지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

        return Map.of();
    }

}
