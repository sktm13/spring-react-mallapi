package org.yujin.mallapi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yujin.mallapi.dto.PageRequestDTO;
import org.yujin.mallapi.dto.PageResponseDTO;
import org.yujin.mallapi.dto.ProductDTO;
import org.yujin.mallapi.service.ProductService;
import org.yujin.mallapi.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor // 외부에서 주입할때 필요
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;


    // // S3 테스트
    // private final S3UploadService s3UploadService;

    // @PostMapping("/test-upload")
    // public String testUpload(@RequestParam("file") MultipartFile file) {
    //     return s3UploadService.upload(file);
    // }
    // // 



    // // 파일조회
    // @GetMapping("/view/{fileName}")
    // public ResponseEntity<Resource> viewFileGET(@PathVariable(name = "fileName") String fileName) {
    //     return fileUtil.getFile(fileName);
    // }

    // 리스트 조회
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        return productService.getList(pageRequestDTO);
    }

    // 상품등록
    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO) {

        // MultipartFile을 먼저 등록해줘야함
        List<MultipartFile> files = productDTO.getFiles();
        // 업로드
        List<String> uploadedFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadedFileNames);

        log.info(uploadedFileNames);

        // register의 반환타입이 Long(pno)임
        Long pno = productService.register(productDTO);

        return Map.of("result", pno);

    }

    // 단품조회
    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable(name = "pno") Long pno) {
        return productService.get(pno);
    }

    // 수정
    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable(name = "pno") Long pno, ProductDTO productDTO) {

        productDTO.setPno(pno);

        // old product
        ProductDTO oldProductDTO = productService.get(pno);

        // file upload
        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        // keep files
        List<String> uploadedFileNames = productDTO.getUploadFileNames();

        if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        productService.modify(productDTO);

        // 기존 파일(oldProduct)과 현재 파일(productDTO)를 매치해서 filter된 것들을 removeFiles에 저장 후 삭제
        List<String> oldFileNames = oldProductDTO.getUploadFileNames();

        if (oldFileNames != null && !oldFileNames.isEmpty()) {
            List<String> removeFiles = oldFileNames.stream()
                    .filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            fileUtil.deleteFiles(removeFiles);
        }

        return Map.of("RESULT", "SUCCESS");

    }

    // 삭제
    @DeleteMapping("/{pno}")
    public Map<String, String> remove(@PathVariable(name = "pno") Long pno) {

        // 해당 pno의 업로드파일 names를 받아와서 fileUtil에서 직접 삭제
        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);

        fileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    }
}
