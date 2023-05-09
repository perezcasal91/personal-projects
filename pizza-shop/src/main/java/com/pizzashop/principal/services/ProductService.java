package com.pizzashop.principal.services;

import com.pizzashop.principal.dtos.ProductRequestDTO;
import com.pizzashop.principal.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    ProductEntity saveProduct(ProductRequestDTO productRequestDTO);

    ProductEntity updateProduct(Long id, ProductRequestDTO toUpdate);

    ProductEntity deleteProduct(Long id);

    List<ProductEntity> findAllProducts();

    List<ProductEntity> findProductsByStatus(String status);

    List<ProductEntity> findProductsByCategoryId(Long categoryId);

    ProductEntity findProductById(Long id);

    ProductEntity findProductByName(String name);

}
