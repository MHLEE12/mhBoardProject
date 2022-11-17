package com.mhboard.api.response;

import com.mhboard.api.domain.Board;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 응답 클래스 분리하기
 */
//@Builder
@Getter
public class BoardResponse {

    private final Long no;
    private final String title;
    private final String content;

    // if 클라이언트 요청) 제목 10글자만 표현할 수 있게 함
//    public String getTitle() {
//        return this.title.substring(0, 10);
//    }

    // 생성자 오버로딩
    public BoardResponse(Board board) {
        this.no = board.getNo();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

    @Builder
    public BoardResponse(Long no, String title, String content) {
        this.no = no;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
