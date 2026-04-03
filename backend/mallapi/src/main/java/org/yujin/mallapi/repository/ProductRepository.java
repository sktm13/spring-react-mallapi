package org.yujin.mallapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yujin.mallapi.domain.Product;
import org.yujin.mallapi.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch{
    
    @EntityGraph(attributePaths = "imageList")
    @Query("SELECT p FROM Product p WHERE p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno); // @Param은 '위 pno가 이 pno야'를 명시 -> 바인딩

    @Modifying
    @Query("UPDATE Product p SET p.delFlag = :delFlag WHERE p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("delFlag") boolean delFlag);

    @Query("select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
