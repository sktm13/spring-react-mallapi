package org.yujin.mallapi.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // -> id필요
@Getter
@Table(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList")//컬렉션이나 연관관계 들어가는 것들 빼놔야함
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;
    private String pdesc;
    private boolean delFlag;

    @ElementCollection // 엔티티에서 해당 컬렉션을 처리하는 것이 좋음
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    // imageList method
    public void changeName(String pname) {
        this.pname = pname;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }
    
    public void addImage(ProductImage image) {
        image.setOrd(imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName) {
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();
        addImage(productImage);
    }

    public void clearList() {
        this.imageList.clear();
    }

}
