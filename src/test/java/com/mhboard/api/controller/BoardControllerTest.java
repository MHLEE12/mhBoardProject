package com.mhboard.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhboard.api.domain.Board;
import com.mhboard.api.repository.BoardRepository;
import com.mhboard.api.request.BoardEdit;
import com.mhboard.api.request.BoardWrite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
                .title("123456789012345")
                .content("내용")
                .build();
        boardRepository.save(board);

        // expected (when + then)
        mockMvc.perform(get("/boards/{no}", board.getNo())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(board.getNo()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void search_boards_test() throws Exception {
        // given
        List<Board> requestBoards = IntStream.range(0, 20)
                .mapToObj(i -> Board.builder()
                        .title("게시글 제목 " + i)
                        .content("게시글 내용 " + i)
                        .build())
                .collect(Collectors.toList());
        boardRepository.saveAll(requestBoards);

        // expected (when + then)
        mockMvc.perform(get("/boards?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].no").value(20))
                .andExpect(jsonPath("$[0].title").value("게시글 제목 19"))
                .andExpect(jsonPath("$[0].content").value("게시글 내용 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void boards_page_test() throws Exception {
        // given
        List<Board> requestBoards = IntStream.range(0, 20)
                .mapToObj(i -> Board.builder()
                        .title("게시글 제목 " + i)
                        .content("게시글 내용 " + i)
                        .build())
                .collect(Collectors.toList());
        boardRepository.saveAll(requestBoards);

        // expected (when + then)
        mockMvc.perform(get("/boards?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].no").value(20))
                .andExpect(jsonPath("$[0].title").value("게시글 제목 19"))
                .andExpect(jsonPath("$[0].content").value("게시글 내용 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void board_modify_test() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        BoardEdit boardEdit = BoardEdit.builder()
                .title("제목 수정 테스트")
                .content("내용")
                .build();

        // expected (when + then)
        mockMvc.perform(patch("/boards/{no}", board.getNo())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void board_delete() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        // expected
        mockMvc.perform(delete("/boards/{no}", board.getNo())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test1() throws Exception {
        // expected
        mockMvc.perform(delete("/boards/{no}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
