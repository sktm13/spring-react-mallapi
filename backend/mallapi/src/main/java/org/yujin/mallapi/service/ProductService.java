package org.yujin.mallapi.service;

import org.springframework.transaction.annotation.Transactional;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.ProductDTO;

@Transactional
public interface ProductService{
    
    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    //product 조회
    ProductDTO get(Long pno);

    //product 수정
    void modify(ProductDTO productDTO);

    //삭제
    void remove(Long pno);
}
