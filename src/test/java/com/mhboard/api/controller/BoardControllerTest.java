package com.mhboard.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/board 요청시 Hello World를 출력한다!")
    void test() throws Exception {
        // 글 제목
        // 글 내용
        // 사용자
            // id
            // name
            // level

        /**
         * json
         * {
         *      "title": "xxx",
         *      "content": "xxx",
         *      "user": {
         *              "id": "xxx",
         *              "name": "xxx"
         *      }
         * }
         */

        // expected
        mockMvc.perform(post("/board")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "글제목입니다.")
                        .param("content", "글 내용입니다.")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"))
                .andDo(MockMvcResultHandlers.print());

    }

}