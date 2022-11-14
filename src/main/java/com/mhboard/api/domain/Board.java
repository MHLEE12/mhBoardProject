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
}
