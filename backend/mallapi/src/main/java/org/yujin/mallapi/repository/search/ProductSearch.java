package org.yujin.mallapi.repository.search;

import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.ProductDTO;

public interface ProductSearch {
    
    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);

}
