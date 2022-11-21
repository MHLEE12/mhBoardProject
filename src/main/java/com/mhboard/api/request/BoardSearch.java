package com.mhboard.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardSearch {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;
    // page size - 한 페이지당 10개의 글

    public long getOffset() {
        return (long) (page - 1) * size;
    }

}
