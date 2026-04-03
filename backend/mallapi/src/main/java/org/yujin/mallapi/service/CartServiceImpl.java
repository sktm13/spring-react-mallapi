package org.yujin.mallapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.yujin.mallapi.domain.Cart;
import org.yujin.mallapi.domain.CartItem;
import org.yujin.mallapi.domain.Member;
import org.yujin.mallapi.domain.Product;
import org.yujin.mallapi.dto.CartItemDTO;
import org.yujin.mallapi.dto.CartItemListDTO;
import org.yujin.mallapi.repository.CartItemRepository;
import org.yujin.mallapi.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO) {

        String email = cartItemDTO.getEmail();
        Long pno = cartItemDTO.getPno();
        int qty = cartItemDTO.getQty();
        Long cino = cartItemDTO.getCino();

        // 장바구니에서 수정할경우에만 발동함******** 그냥 display된 상품은 product이므로 cino가 없음
        if (cino != null) {

            Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);

            CartItem cartItem = cartItemResult.orElseThrow();

            cartItem.changeQty(qty);

            cartItemRepository.save(cartItem);

            return getCartItems(email);
        }

        // 상품 목록에서 클릭했을때 발동
        Cart cart = getCart(email);

        CartItem cartItem = null;

        cartItem = cartItemRepository.getItemOfPno(email, pno);

        if (cartItem == null) {
            Product product = Product.builder().pno(pno).build();

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .qty(qty)
                    .build();
        } else {
            cartItem.changeQty(qty);
        }
        cartItemRepository.save(cartItem);

        return getCartItems(email);
    }

    // 카트 불러오기(없으면 자동생성) 메소드
    private Cart getCart(String email) {

        Cart cart = null;

        // 해당 email의 Cart가 있으면 반환
        Optional<Cart> result = cartRepository.getCartOfMember(email);

        // 없으면 Cart 객채 생성하고 추가 후 반환
        if (result.isEmpty()) {

            log.info("카트가 아직 존재하지 않음");

            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().owner(member).build();
            cart = cartRepository.save(tempCart);
            log.info("카트 생성 완료");

        } else {
            log.info("기존 카트 불러오기 완료");
            cart = result.get();

        }

        return cart;

    }

    @Override
    public List<CartItemListDTO> getCartItems(String email) {
        return cartItemRepository.getItemsOfCartDTOByEmail(email);
    }

    @Override
    public List<CartItemListDTO> remove(Long cino) {

        //삭제 후에 카트를 다시 불러와야하기때문에 삭제 전에 cno 확보****
        Long cno = cartItemRepository.getCartFromItem(cino);

        log.info("cart no: " + cno);

        cartItemRepository.deleteById(cino);

        return cartItemRepository.getItemsOfCartDTOByCart(cno);
    }

}
