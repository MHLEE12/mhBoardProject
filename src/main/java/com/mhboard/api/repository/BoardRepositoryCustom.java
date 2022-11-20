package com.mhboard.api.repository;

import com.mhboard.api.domain.Board;
import com.mhboard.api.request.BoardSearch;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> getList(BoardSearch boardSearch);
}
