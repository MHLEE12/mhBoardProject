package com.mhboard.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhboard.api.domain.Board;
import com.mhboard.api.repository.BoardRepository;
import com.mhboard.api.request.BoardWrite;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("/board 요청시 Hello World를 출력한다!")
    void board_test() throws Exception {
        // given
//        BoardWrite request = new BoardWrite("제목입니다.", "내용입니다.");
        BoardWrite request = BoardWrite.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // ObjectMapper는 실무에서 많이 사용함
        String json = objectMapper.writeValueAsString(request);
//        System.out.println("json = " + json);

        // expected
        mockMvc.perform(post("/boards")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/board 요청시 title값은 필수다.")
    void title_test() throws Exception {
        // given
        BoardWrite request = BoardWrite.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/boards")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/board 요청시 DB에 값이 저장된다.")
    void writeTest() throws Exception {
        // given
        BoardWrite request = BoardWrite.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/boards")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, boardRepository.count());

        Board board = boardRepository.findAll().get(0);
        assertEquals("제목입니다.", board.getTitle());
        assertEquals("내용입니다.", board.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void search_one_board_test() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        // expected (when + then)
        mockMvc.perform(get("/boards/{no}", board.getNo())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(board.getNo()))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andDo(print());

    }

}