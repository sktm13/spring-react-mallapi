package org.yujin.mallapi.service;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yujin.mallapi.dto.ProductDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @org.springframework.beans.factory.annotation.Autowired
    private ProductService productService;

    @org.junit.jupiter.api.Test
    public void testList() {

        org.yujin.mallapi.dto.PageRequestDTO pageRequestDTO = org.yujin.mallapi.dto.PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        org.yujin.mallapi.dto.PageResponseDTO<org.yujin.mallapi.dto.ProductDTO> responseDTO = productService
                .getList(pageRequestDTO);

        log.info(responseDTO.getDtoList());

    }

    @Test
    public void testRegister() {
        ProductDTO productDTO = ProductDTO.builder()
                .pname("새로운 상품")
                .pdesc("신규 추가 상품입니다.")
                .price(1000)
                .build();
        // uuid가 있어야 함
        productDTO.setUploadFileNames(
                List.of(UUID.randomUUID() + "_" + "Test1.jpg",
                        UUID.randomUUID() + "_" + "Test2.jpg"));
        productService.register(productDTO);
    }

}
