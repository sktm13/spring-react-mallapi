package org.yujin.mallapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yujin.mallapi.domain.Product;
import org.yujin.mallapi.domain.ProductImage;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.ProductDTO;
import org.yujin.mallapi.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceimpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

        // 1. pageable로 뽑아옴
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        // 2. object 배열이 반환타입임(상품, 상품이미지0번) -> productDTO같은걸로 바꿔야함
        Page<Object[]> result = productRepository.selectList(pageable);
        // object[] => 0 product 1 productImage
        // object[] => 0 product 1 productImage
        // object[] => 0 product 1 productImage

        // get() 은 stream => ProductDTO로 바꿔서 List로 반환할꺼임
        List<ProductDTO> dtoList = result.get().map(arr -> {
            ProductDTO productDTO = null;

            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            productDTO = ProductDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .price(product.getPrice())
                    .pdesc(product.getPdesc())
                    .build();

            String imageStr = productImage.getFileName();
            productDTO.setUploadFileNames(List.of(imageStr));

            return productDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public Long register(ProductDTO productDTO) {

        Product product = dtoToEntity(productDTO);

        log.info("--------------------------");
        log.info(product);
        log.info(product.getImageList());

        Long pno = productRepository.save(product).getPno();

        return pno;
    }

    @Override
    public ProductDTO get(Long pno) {

        // findByID를 쓰기위해 Optional로 가져옴
        Optional<Product> result = productRepository.findById(pno);

        // 문제가 있으면 예외처리
        Product product = result.orElseThrow();

        // entity를 dto로 변환해서 처리
        ProductDTO productDTO = entityToDTO(product);

        return productDTO;

    }

    // dto를 entity로 변환하는 메소드, 내부에서만 사용할거라 private 달아줌
    private Product dtoToEntity(ProductDTO productDTO) {

        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .price(productDTO.getPrice())
                .pdesc(productDTO.getPdesc())
                .build();

        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if (uploadFileNames == null || uploadFileNames.size() == 0) {
            product.addImageString("default.jpeg");
            return product;
        }

        uploadFileNames.forEach(fileName -> {
            product.addImageString(fileName);
        });

        return product;
    }

    // entity to dto (조회할때에는 데이터베이스의 entity타입에서 dto로 브라우저로 전달)
    private ProductDTO entityToDTO(Product product) {
        // entityToDTO
        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .delFlag(product.isDelFlag())
                .build();
        List<ProductImage> imageList = product.getImageList();

        if (imageList == null || imageList.isEmpty()) {
            return productDTO;
        }

        List<String> fileNameList = imageList.stream()
                .map(productImage -> productImage.getFileName())
                .toList();

        productDTO.setUploadFileNames(fileNameList);

        return productDTO;
    }

    @Override
    public void modify(ProductDTO productDTO) {

        // 조회
        Optional<Product> result = productRepository.findById(productDTO.getPno());
        Product product = result.orElseThrow();// 예외처리

        // 수정
        product.changePrice(productDTO.getPrice());
        product.changeName(productDTO.getPname());
        product.changeDesc(productDTO.getPdesc());
        product.changeDel(productDTO.isDelFlag());

        // 이미지 처리
        List<String> uploadFileNames = productDTO.getUploadFileNames();

        product.clearList();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {

            uploadFileNames.forEach(fileName -> {
                product.addImageString(fileName);
            });

        }

        // 저장
        productRepository.save(product);

    }

    @Override
    public void remove(Long pno) {

        productRepository.deleteById(pno);
        
    }
}
