package org.yujin.mallapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yujin.mallapi.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    
    @Query("SELECT c FROM Cart c WHERE c.owner.email = :email")
    Optional<Cart> getCartOfMember(@Param("email") String email);
    
}
