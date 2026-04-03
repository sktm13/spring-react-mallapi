package org.yujin.mallapi.service;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.TodoDTO;

import lombok.extern.log4j.Log4j2;

//@Transactional
@SpringBootTest
@Log4j2
public class TodoServiceTests {
    // TodoService를 이용해서 register 테스트
    @Autowired
    private TodoService todoService;

    //테스트용 데이터 넣기 (각 테스트마다 추가해주고 삭제하는 것이 좋음 삭제는 보통 트렌젝셔널 어노테이션으로 자동rollback)
    @Test
    public void testInsert() {

        for (int i = 1; i <= 100; i++) {

            TodoDTO dto = TodoDTO.builder()
                    .title("테스트 제목 " + i)
                    .writer("user" + (i % 10))
                    .complete(false)
                    .dueDate(LocalDate.now().plusDays(i))
                    .build();

            todoService.register(dto);
        }
    }


    @Disabled
    @Test
    public void testRegister() {
        TodoDTO todoDTO = TodoDTO.builder()
                .title("Service Test Title")
                .writer("tester")
                .dueDate(LocalDate.of(2024, 12, 31))
                .build();

        Long tno = todoService.register(todoDTO);

        Assertions.assertNotNull(tno);
    }

    // **매우중요, 테스트는 각 메소드가 의존하면 안됨. (데이터 입력, 테스트, 출력, 삭제)를 한쌍으로 해야함
    @Disabled
    @Test
    public void testRead() {
        Long tno = 1L;
        TodoDTO todoDTO = todoService.get(tno);
        log.info(todoDTO);

    }

    // modify에 대한 테스트 코드
    @Disabled
    @Test
    public void testModify() {

        //입력

        //테스트
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(1L)
                .title("Test Update Title")
                .complete(true).dueDate(LocalDate.of(2025, 12, 31))
                .build();

        todoService.modify(todoDTO);

        //삭제
    }

    //페이징처리를 할때는 로그에 limit처리가 되어있는지 꼭 확인해야함
    @Disabled
    @Test
    public void testList() {
        log.info("-----------------");

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<TodoDTO> responseDTO = todoService.list(pageRequestDTO);

        log.info(responseDTO);
        log.info(responseDTO.getPageNumList());

    }
}
