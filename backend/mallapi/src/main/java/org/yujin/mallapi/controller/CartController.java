package org.yujin.mallapi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yujin.mallapi.dto.CartItemDTO;
import org.yujin.mallapi.dto.CartItemListDTO;
import org.yujin.mallapi.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    // 카트 내용 변화
    @PreAuthorize("#itemDTO.email == authentication.name") // DTO의 사용자와 현재 검증된 사용자가 같으면
    @PostMapping("/change")
    public List<CartItemListDTO> changeCart(@RequestBody CartItemDTO itemDTO) {

        log.info(itemDTO);

        if (itemDTO.getQty() <= 0) {
            return cartService.remove(itemDTO.getCino());
        }

        return cartService.addOrModify(itemDTO);

    }

    // 사용자의 카트를 불러옴
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/items") // security에 저장된 회원 정보
    public List<CartItemListDTO> getCartitems(Principal principal) {
        String email = principal.getName();

        log.info("email: " + email);

        return cartService.getCartItems(email);
    }

    // 카트 삭제
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{cino}")
    public List<CartItemListDTO> removeFromCart(@PathVariable("cino") Long cino) {
        log.info("cart item no(remove): " + cino);
        return cartService.remove(cino);
    }

}
