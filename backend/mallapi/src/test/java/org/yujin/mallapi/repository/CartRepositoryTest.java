package org.yujin.mallapi.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.yujin.mallapi.domain.Cart;
import org.yujin.mallapi.domain.CartItem;
import org.yujin.mallapi.domain.Member;
import org.yujin.mallapi.domain.Product;
import org.yujin.mallapi.dto.CartItemListDTO;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;


@SpringBootTest
@Log4j2
public class CartRepositoryTest {
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional  //
    @Commit         // 실제로 db에 반영하고싶을때
    @Test
    public void testInsertByProduct(){

        log.info("---Cartitem insert test---");

        String email = "user1@aaa.com";
        Long pno = 13L;
        int qty = 1;

        //이메일 상품번호로 이미있는지 확인해서 (넣거나 수량만증가)
        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        //이미 존재? 수량만 조정
        if(cartItem != null){
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return;
        }

        //사용자의 장바구니에 장바구니 아이템을 만들어서 저장
        //장바구니 자체의 존재 판별
        Optional<Cart> result = cartRepository.getCartOfMember(email);

        Cart cart = null;

        if(result.isEmpty()){
            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().owner(member).build();
            cart = cartRepository.save(tempCart);
        } else { //장바구니는 있음 but 해당 상품은 장바구니에 없음
            cart = result.get();
        }

        //장바구니안에 해당 아이템이 없다
        if(cartItem == null){
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().cart(cart).product(product).qty(qty).build();
        }

        cartItemRepository.save(cartItem);




    }

    //email로 해당 유저의 장바구니 불러옴
    @Test
    public void testListOfMember(){
        String email = "user1@aaa.com";
        List<CartItemListDTO> cartItemListDTOList = cartItemRepository.getItemsOfCartDTOByEmail(email);

        for(CartItemListDTO dto : cartItemListDTOList){
            log.info(dto);
        }
    }

    //수량변경 test
    @Transactional
    @Commit
    @Test
    public void testUpdateByCino(){

        Long cino = 1L;
        int qty = 4;

        Optional<CartItem> result = cartItemRepository.findById(cino);

        CartItem cartItem = result.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);

    }

    @Test
    public void testDeleteThenList(){
        Long cino = 1L;

        Long cno = cartItemRepository.getCartFromItem(cino);

        cartItemRepository.deleteById(cino);

        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        for(CartItemListDTO dto : cartItemList){
            log.info(dto);
        }
    }
}
