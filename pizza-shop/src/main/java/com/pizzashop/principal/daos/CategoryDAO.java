package com.pizzashop.principal.daos;

import com.pizzashop.principal.entities.CategoryEntity;

public interface CategoryDAO {

    CategoryEntity findCategoryWithProductsById(Long id);

}
