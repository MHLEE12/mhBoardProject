package com.mhboard.api.service;

import com.mhboard.api.domain.Board;
import com.mhboard.api.repository.BoardRepository;
import com.mhboard.api.request.BoardWrite;
import com.mhboard.api.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(BoardWrite boardWrite) {
        // boardWrite(DTO) -> Entity 형태로 변환
        Board board = Board.builder()
                .title(boardWrite.getTitle())
                .content(boardWrite.getContent())
                .build();
        boardRepository.save(board);
    }

    public BoardResponse get(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다."));

        BoardResponse response = BoardResponse.builder()
                .no(board.getNo())
                .title(board.getTitle())
                .content(board.getContent())
                .build();

        return response;
    }
}
