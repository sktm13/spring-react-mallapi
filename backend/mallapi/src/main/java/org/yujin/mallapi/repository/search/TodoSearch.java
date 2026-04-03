package org.yujin.mallapi.repository.search;

import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.TodoDTO;

public interface TodoSearch {
    
    PageResponseDTO<TodoDTO> search(String query, PageRequestDTO pageRequestDTO);

    
}
