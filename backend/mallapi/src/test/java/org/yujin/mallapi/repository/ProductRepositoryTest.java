package org.yujin.mallapi.repository;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.yujin.mallapi.domain.Product;
import org.yujin.mallapi.dto.PageRequestDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .pname("Product " + i)
                    .price(1000 * i)
                    .pdesc("Description " + i)
                    .build();

            product.addImageString(java.util.UUID.randomUUID().toString() + "_" + "IMAGE1.jpg");
            product.addImageString(java.util.UUID.randomUUID().toString() + "_" + "IMAGE2.jpg");

            productRepository.save(product);
        }
    }

    @Test
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });

    }

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);
    }
}
