package org.yujin.mallapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {
    
    private Long cino;
    private int qty;
    private String pname;
    private Long pno;
    private int price;
    private String imageFile;

    public CartItemListDTO(Long cino, int qty, String pname, Long pno, int price, String imageFile) {
        this.cino = cino;
        this.qty = qty;
        this.pname = pname;
        this.pno = pno;
        this.price = price;
        this.imageFile = imageFile;
    }
}
