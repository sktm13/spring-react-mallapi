package org.yujin.mallapi.repository.search;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.yujin.mallapi.domain.QTodo;
import org.yujin.mallapi.domain.Todo;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.TodoDTO;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class TodoSearchImpl implements TodoSearch {

    private final JPQLQueryFactory queryFactory;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<TodoDTO> search(String keyword, PageRequestDTO pageRequest) {

        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> query = queryFactory.selectFrom(todo);

        query.where(todo.title.contains(keyword));

        // tno역순으로 정렬 (가장 최신것이 1번)
        query.orderBy(todo.tno.desc());

        // 페이징처리
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize());
        query.offset(pageable.getOffset()); //시작조건
        query.limit(pageable.getPageSize()); 

        log.info("-----------");
        log.info(query);
        log.info("-----------");

        // 동적검색
        List<Todo> list = query.fetch();
        log.info(list);

        long count = query.fetchCount();
        log.info(count);

        List<TodoDTO> dtoList = list.stream()
                .map(todo1 -> modelMapper.map(todo1, TodoDTO.class))
                .collect(java.util.stream.Collectors.toList());

        return PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequest)
                .totalCount(count)
                .build();

    }
}
