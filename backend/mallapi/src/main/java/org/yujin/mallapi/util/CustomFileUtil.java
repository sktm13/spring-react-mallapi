package org.yujin.mallapi.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Component
@Log4j2
@RequiredArgsConstructor // 의존성주입 필요할때
public class CustomFileUtil {

    @Value("${org.yujin.upload.path}")
    private String uploadPath;
    // C:\Users\sktm1\Desktop\project\backend\mallapi/upload

    @PostConstruct // 생성자 대신 초기화할때 종종 사용, 폴더 만들어주는 용도로 사용
    public void init() {

        File tempFodler = new File(uploadPath);
        if (!tempFodler.exists()) {
            tempFodler.mkdirs();
        }

        uploadPath = tempFodler.getAbsolutePath();

        log.info("---------------------------");
        log.info(uploadPath);
    }

    // 파일저장
    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

        if (files == null || files.size() == 0) {
            return List.of(); // 비어있는 것 반환하는 방법, null보다 안전(NULL체크 안해도됨)
        }

        List<String> uploadedNames = new ArrayList<>();

        for (MultipartFile file : files) {

            //파일이름이 없거나 비어있을때 그냥 진행되도록
            if(file.getOriginalFilename() == null || file.getOriginalFilename().equals("")){
                continue;
            }


            String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING); // 원본파일 업로드 , StandardCopyOption.REPLACE_EXISTING << 같은이름있어도 오류안남, 덮어쓰기

                String contentType = file.getContentType(); // Mime type, 파일의 타입을 추출해서

                if (contentType != null && contentType.startsWith("image")) { // 이미지로 시작하면 썸네일 생성

                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);

                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile());

                }
                uploadedNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadedNames;
    }

    // 파일 조회
    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if (!resource.isReadable()) {
            resource = new FileSystemResource(uploadPath + File.separator + "default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(headers).body(resource);

    }

    // 파일 삭제
    public void deleteFiles(List<String> fileNames) {

        if (fileNames == null || fileNames.size() == 0) {
            return;
        }

        fileNames.forEach(fileName -> {

            // 썸네일 삭제
            String thumbnailFileName = "s_" + fileName;

            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(thumbnailPath);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        });
    }
}
