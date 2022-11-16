package com.mhboard.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String title;

    @Lob
    private String content;

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 제목 10글자로 제한
//    public String getTitle() {
//        // 엔티티에 getter 메소드를 만들 때에는
//        // 서비스의 정책을 절대 넣지 마세요!
//        return this.title.substring(0, 10);
//    }
}
