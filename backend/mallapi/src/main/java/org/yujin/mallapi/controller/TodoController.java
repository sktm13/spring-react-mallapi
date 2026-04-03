package org.yujin.mallapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.TodoDTO;
import org.yujin.mallapi.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {

    // TodoService 주입
    private final TodoService todoService;

    // 특정 tno의 todo 조회
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) { // ("tno")로 명시해줘야함 아니면 gradle설정

        return todoService.get(tno);

    }

    // list/page=1&size=10 호출 처리
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        return todoService.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO) {
        log.info("TodoDTO: " + todoDTO);
        Long tno = todoService.register(todoDTO);
        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(
            @PathVariable(name = "tno") Long tno,
            @RequestBody TodoDTO todoDTO) {

        todoDTO.setTno(tno);

        log.info("Modify: " + todoDTO);

        todoService.modify(todoDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    //역시나 삭제는 잘 안씀.
    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable(name = "tno") Long tno) {
        log.info("Remove: " + tno);
        todoService.remove(tno);
        return Map.of("RESULT", "SUCCESS");
    }
}
