package com.mhboard.api.controller;

import com.mhboard.api.request.BoardEdit;
import com.mhboard.api.request.BoardSearch;
import com.mhboard.api.request.BoardWrite;
import com.mhboard.api.response.BoardResponse;
import com.mhboard.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public void board(@RequestBody @Valid BoardWrite request) {
        // Case1. 저장한 데이터 Entity -> response로 응답하기
        // Case2. 저장한 데이터의 primary_id ->response로 응답하기
        //        Client에서는 수신한 id를 글 조회 API를 통해서 글 데이터를 수신받음
        // Case3. 응답 필요 없음 -> Client에서 모든 board 데이터 context를 잘 관리함
        //        (글을 작성하면 목록으로 가니까 굳이 작성글 데이터를 가지고 있을 필요가 없다)
        // Bad Case : 서버에서 반드시 이렇게 할 것이다라고 fix 하는 것
        //            -> 서버에서 유연하게 대응하는 것이 good (코드를 잘 짜야함)
        //            -> 한 번에 일괄적으로 잘 처리되는 케이스가 없습니다. -> 잘 관리하는 형태가 중요!

        boardService.write(request);
    }

    // 여러개의 글 조회 API
    @GetMapping("/boards")
    public List<BoardResponse> getList(@ModelAttribute BoardSearch boardSearch) {

        return boardService.getList(boardSearch);
    }

    // 글 1개 조회
    @GetMapping("/boards/{no}")
    public BoardResponse get(@PathVariable Long no) {

        return boardService.get(no);
    }

    @PatchMapping("/boards/{boardNo}")
    public void edit(@PathVariable Long boardNo, @RequestBody @Valid BoardEdit request) {
        boardService.edit(boardNo, request);
    }

    @DeleteMapping("/boards/{no}")
    public void delete(@PathVariable Long no) {
        boardService.delete(no);
    }

}
