package com.mhboard.api.service;

import com.mhboard.api.domain.Board;
import com.mhboard.api.repository.BoardRepository;
import com.mhboard.api.request.BoardSearch;
import com.mhboard.api.request.BoardWrite;
import com.mhboard.api.response.BoardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        BoardWrite boardWrite = BoardWrite.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        boardService.write(boardWrite);

        // then
        assertEquals(1L, boardRepository.count());
        Board board = boardRepository.findAll().get(0);
        assertEquals("제목입니다.", board.getTitle());
        assertEquals("내용입니다.", board.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Board saveBoard = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(saveBoard);

        // when
        BoardResponse response = boardService.get(saveBoard.getNo());

        //then
        assertNotNull(response);
        assertEquals(1L, boardRepository.count());
        assertEquals("제목", response.getTitle());
        assertEquals("내용", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Board> requestBoards = IntStream.range(0, 20)
                        .mapToObj(i -> Board.builder()
                                .title("게시글 제목 " + i)
                                .content("게시글 내용 " + i)
                                .build())
                        .collect(Collectors.toList());
        boardRepository.saveAll(requestBoards);

        BoardSearch boardSearch = BoardSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<BoardResponse> boards = boardService.getList(boardSearch);

        //then
        assertEquals(10L, boards.size());
        assertEquals("게시글 제목 19", boards.get(0).getTitle());
    }


}