package com.mhboard.api.controller;

import com.mhboard.api.domain.Board;
import com.mhboard.api.request.BoardWrite;
import com.mhboard.api.service.BoardService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public Board board(@RequestBody @Valid BoardWrite request) {
        // Case1. 저장한 데이터 Entity -> response로 응답하기


        return boardService.write(request);
    }

}
