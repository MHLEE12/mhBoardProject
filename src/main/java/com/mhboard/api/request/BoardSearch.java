package com.mhboard.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearch {

    private int page;
    private int size; // page size - 한 페이지당 몇개의 글

    @Builder
    public BoardSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
