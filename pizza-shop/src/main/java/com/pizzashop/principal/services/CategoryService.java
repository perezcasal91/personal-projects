package com.pizzashop.principal.services;

import com.pizzashop.principal.dtos.CategoryRequestDTO;
import com.pizzashop.principal.entities.CategoryEntity;

import java.util.List;

public interface CategoryService {

    CategoryEntity saveCategory(CategoryRequestDTO categoryRequestDTO);

    CategoryEntity updateCategory(Long id, CategoryRequestDTO toUpdate);

    CategoryEntity deleteCategory(Long id);

    List<CategoryEntity> findAllCategories();

    CategoryEntity findCategoryById(Long id);

    CategoryEntity findCategoryByName(String name);
}
