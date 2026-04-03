package org.yujin.mallapi.service;

import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.TodoDTO;

public interface TodoService {
    
    Long register(TodoDTO todo);
    TodoDTO get(Long tno);
    void modify(TodoDTO todo);
    void remove(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
