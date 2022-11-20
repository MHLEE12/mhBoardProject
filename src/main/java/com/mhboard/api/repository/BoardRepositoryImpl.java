package com.mhboard.api.repository;

import com.mhboard.api.domain.Board;
import com.mhboard.api.domain.QBoard;
import com.mhboard.api.request.BoardSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> getList(BoardSearch boardSearch) {
        // 10개만 가져오는 쿼리
        return jpaQueryFactory.selectFrom(QBoard.board)
                .limit(boardSearch.getSize())
                .offset((long) (boardSearch.getPage() - 1) * boardSearch.getSize())
                .orderBy(QBoard.board.no.desc())
                .fetch();
    }
}
