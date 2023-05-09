package com.pizzashop.principal.repositories;

import com.pizzashop.principal.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findCategoryById(Long id);

    CategoryEntity findCategoryByName(String name);

    boolean existsCategoryByName(String name);

}
