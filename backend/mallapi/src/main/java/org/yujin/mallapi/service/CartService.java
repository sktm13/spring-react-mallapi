package org.yujin.mallapi.service;

import java.util.List;

import org.yujin.mallapi.dto.CartItemDTO;
import org.yujin.mallapi.dto.CartItemListDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface CartService {
    
    List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO);

    List<CartItemListDTO> getCartItems(String email);

    List<CartItemListDTO> remove(Long cino);
}
