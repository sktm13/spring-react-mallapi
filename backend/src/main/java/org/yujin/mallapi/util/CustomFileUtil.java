package org.yujin.mallapi.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> saveFiles(List<MultipartFile> files) {

        if (files == null || files.isEmpty()) {
            return List.of();
        }

        List<String> uploadedNames = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
                continue;
            }

            try {
                String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();

                // 원본 업로드
                PutObjectRequest originalRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(savedName)
                        .contentType(file.getContentType())
                        .build();

                s3Client.putObject(
                        originalRequest,
                        RequestBody.fromBytes(file.getBytes())
                );

                // 썸네일 업로드
                String contentType = file.getContentType();

                if (contentType != null && contentType.startsWith("image")) {

                    ByteArrayOutputStream thumbnailOutputStream = new ByteArrayOutputStream();

                    Thumbnails.of(file.getInputStream())
                            .size(200, 200)
                            .toOutputStream(thumbnailOutputStream);

                    PutObjectRequest thumbRequest = PutObjectRequest.builder()
                            .bucket(bucket)
                            .key("s_" + savedName)
                            .contentType(contentType)
                            .build();

                    s3Client.putObject(
                            thumbRequest,
                            RequestBody.fromBytes(thumbnailOutputStream.toByteArray())
                    );
                }

                uploadedNames.add(savedName);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return uploadedNames;
    }

    public void deleteFiles(List<String> fileNames) {

        if (fileNames == null || fileNames.isEmpty()) {
            return;
        }

        for (String fileName : fileNames) {

            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucket)
                            .key(fileName)
                            .build()
            );

            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucket)
                            .key("s_" + fileName)
                            .build()
            );
        }
    }
}