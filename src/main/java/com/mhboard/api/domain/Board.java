package com.mhboard.api.domain;

import lombok.*;

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

    public BoardEditor.BoardEditorBuilder toEditor() {
        // board service에서 build fix하기 위해 여기서 fix하지 않고 보냄
         return BoardEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(BoardEditor boardEditor) {
        title = boardEditor.getTitle();
        content = boardEditor.getContent();
    }

}
