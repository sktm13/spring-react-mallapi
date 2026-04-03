package org.yujin.mallapi.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
   
    //파일은 db에 넣으면 안됨. 성능 안나옴(보안상 중요한 것은 제외)
    private Long pno;

    private String pname;

    private int price;

    //설명
    private String pdesc;

    //실제 삭제는 잘안함. soft delete
    private boolean delFlag;


    //Null check 없이하기위해 ArrayList사용
    //브라우저에서 들어가고 나오는 실제 파일데이터값
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    //데이터베이스에 있는 파일String값
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

}
