package org.yujin.mallapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yujin.mallapi.domain.QTodo;
import org.yujin.mallapi.domain.Todo;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.TodoDTO;

import com.querydsl.jpa.JPQLQueryFactory;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    // todoRepository를 이용해서 crud 테스트 코드 필요 *
    @org.springframework.beans.factory.annotation.Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JPQLQueryFactory queryFactory;

    @org.junit.jupiter.api.Test
    public void testInsert() {
        for (int i = 1; i <= 100; i++) {
            org.yujin.mallapi.domain.Todo todo = org.yujin.mallapi.domain.Todo.builder()
                    .title("Title..." + i)
                    .dueDate(java.time.LocalDate.of(2024, 12, 31))
                    .writer("user" + (i % 10))
                    .build();

            todoRepository.save(todo);
        }
    }

    @org.junit.jupiter.api.Test
    public void testRead() {
        Long tno = 33L;
        java.util.Optional<org.yujin.mallapi.domain.Todo> result = todoRepository.findById(tno);
        org.yujin.mallapi.domain.Todo todo = result.orElseThrow();
        log.info(todo);
    }

    @org.junit.jupiter.api.Test
    public void testUpdate() {
        Long tno = 33L;
        java.util.Optional<org.yujin.mallapi.domain.Todo> result = todoRepository.findById(tno);
        org.yujin.mallapi.domain.Todo todo = result.orElseThrow();

        // Entity update logic (usually handled by setter or change methods)
        // Since only Getter is provided in the domain, you might need to add setters or
        // change methods there.
        // Assuming we can use reflection or if you add methods later:
        todo.changeTitle("Update Title...");
        todo.changeComplete(true);
        todo.changeDueDate(java.time.LocalDate.of(2024, 10, 10));

        // Save the updated entity

        todoRepository.save(todo);
    }

    @org.junit.jupiter.api.Test
    public void testDelete() {
        Long tno = 1L;
        todoRepository.deleteById(tno);
    }

    @org.junit.jupiter.api.Test
    public void testPaging() {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10,
                org.springframework.data.domain.Sort.by("tno").descending());

        // findAll
        todoRepository.findAll(pageable).forEach(todo -> {
            log.info(todo);
        });

        Page<Todo> result = todoRepository.findAll(pageable);
        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());
        log.info("has next page?: " + result.hasNext());
        log.info("first page?: " + result.isFirst());

    }

    // title로 검색하는 페이징처리
    @org.junit.jupiter.api.Test
    public void testSearch() {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10,
                org.springframework.data.domain.Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findByTitleContaining("1", pageable);

        result.getContent().forEach(todo -> log.info(todo));

        log.info("TOTAL: " + result.getTotalElements());
    }

    // QTodo를 이용해서 title로 '11'이라는 글자가 있는 데이터 검색
    @org.junit.jupiter.api.Test
    public void testQuerydsl() {
        // org.springframework.data.domain.Pageable pageable =
        // org.springframework.data.domain.PageRequest.of(0, 10,
        // org.springframework.data.domain.Sort.by("tno").descending());

        // JPQLQueryFactory를 이용해서 검색
        QTodo qTodo = QTodo.todo;
        java.util.List<Todo> list = queryFactory.select(qTodo).from(qTodo).where(qTodo.title.contains("11")).fetch();

        log.info(list);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(2)
                .size(10)
                .build();
        PageResponseDTO<TodoDTO> response = todoRepository.search("11",pageRequestDTO);
        log.info(response);
    }

}
