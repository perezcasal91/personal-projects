package com.pizzashop.principal.daos;

import com.pizzashop.principal.entities.ProductEntity;

import java.util.List;

public interface ProductDAO {

    List<ProductEntity> mostSellProducts();
}
