package org.yujin.mallapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yujin.mallapi.domain.CartItem;
import org.yujin.mallapi.dto.CartItemListDTO;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    //특정한 사용자의 모든 장바구니 아이템들을 가져올 경우 // input -> email // ouput -> CartItemListDTO
    @Query(" select " +                                         
            " new org.yujin.mallapi.dto.CartItemListDTO(ci.cino, ci.qty, p.pname, p.pno, p.price, pi.fileName)"+ // CartItemListDTO를 그때그때 생성해서 건네준다**************
            " from" +
            " CartItem ci inner join Cart mc on ci.cart = mc "+ // CartItem에 지정된 Cart와 같다면 이너조인
            " left join Product p on ci.product = p" +          // CartItem에 지정된 Product와 같다면 왼쪽조인
            " left join p.imageList pi"+                        // 해당 product에 있는 imageList를 가져온다
            " where " +
            " pi.ord = 0 and "+                                 // 조건 : pi 중 1번째이미지, 카트 주인(멤버)의 이메일이 파라미터로 주어진 이메일과 같다면, ci들을 cino의 내림차순으로
            " mc.owner.email = :email order by ci.cino desc")
    List<CartItemListDTO> getItemsOfCartDTOByEmail(@Param("email") String email);


    //이메일, 상품번호로 해당 상품이 이미 존재하는지 판단
    @Query("select ci from CartItem ci left join Cart c on ci.cart = c "+
            " where c.owner.email = :email and ci.product.pno = :pno")
    CartItem getItemOfPno(@Param("email") String email, @Param("pno") Long pno);
    
    //장바구니 아이템 번호로 장바구니 번호를 얻어오려고 하는 경우
    //ex: 1,2,3번 중에 2번삭제 -> 1,3번 가져옴
    @Query("select c.cno from Cart c left join CartItem ci on ci.cart = c where ci.cino = :cino")
    Long getCartFromItem(@Param("cino") Long cino);

    //장바구니 번호로 모든 장바구니 아이템들 조회
    @Query(" select " + 
            " new org.yujin.mallapi.dto.CartItemListDTO(ci.cino, ci.qty, p.pname,p.pno, p.price, pi.fileName) " +
            " from CartItem ci " +
            " inner join Cart mc on ci.cart = mc " +
            " left join Product p on ci.product = p " +
            " left join p.imageList pi "+
            " where pi.ord = 0 and mc.cno = :cno " + 
            " order by ci.cino desc ")
    List<CartItemListDTO> getItemsOfCartDTOByCart(@Param("cno") Long cno);

}
