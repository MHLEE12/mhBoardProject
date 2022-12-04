package com.mhboard.api.service;

import com.mhboard.api.domain.Board;
import com.mhboard.api.domain.BoardEditor;
import com.mhboard.api.exception.BoardNotFound;
import com.mhboard.api.repository.BoardRepository;
import com.mhboard.api.request.BoardEdit;
import com.mhboard.api.request.BoardSearch;
import com.mhboard.api.request.BoardWrite;
import com.mhboard.api.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(BoardNotFound::new);

        return BoardResponse.builder()
                .no(board.getNo())
                .title(board.getTitle())
                .content(board.getContent())
                .build();

    }

    public List<BoardResponse> getList(BoardSearch boardSearch) {
        return boardRepository.getList(boardSearch).stream()
//                .map(board -> new BoardResponse(board))
                // 위의 것을 밑의 코드로 표현
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long no, BoardEdit boardEdit) {
        Board board = boardRepository.findById(no)
                .orElseThrow(BoardNotFound::new);

        BoardEditor.BoardEditorBuilder editorBuilder = board.toEditor();

        BoardEditor boardEditor = editorBuilder.title(boardEdit.getTitle())
                .content(boardEdit.getContent())
                .build();

        board.edit(boardEditor);
    }

    public void delete(Long no) {
        Board board = boardRepository.findById(no)
                .orElseThrow(BoardNotFound::new);

        boardRepository.delete(board);
    }
}
