package com.pizzashop.principal.repositories;

import com.pizzashop.principal.entities.ProductEntity;
import com.pizzashop.principal.entities.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findProductById(Long id);

    ProductEntity findProductByName(String name);

    List<ProductEntity> findProductsByStatus(ProductStatus status);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    List<ProductEntity> findProductsByCategoryId(@Param("categoryId") Long categoryId);

    boolean existsProductByName(String name);

}
