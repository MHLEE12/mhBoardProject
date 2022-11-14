package com.mhboard.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class BoardWrite {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public BoardWrite(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        // InvalidDefinitionException 때문에 @JsonProperty 어노테이션 사용
        this.title = title;
        this.content = content;
    }

}
