package com.pizzashop.principal.services.impls;

import com.pizzashop.principal.daos.CategoryDAO;
import com.pizzashop.principal.dtos.CategoryRequestDTO;
import com.pizzashop.principal.entities.CategoryEntity;
import com.pizzashop.principal.exceptions.EntityNotFoundException;
import com.pizzashop.principal.exceptions.NameAlreadyExistsException;
import com.pizzashop.principal.repositories.CategoryRepository;
import com.pizzashop.principal.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDAO categoryDAO;

    /**
     * Save a category from DTO into the database using JPA.
     *
     * @param categoryRequestDTO CategoryDTO as request.
     * @return CategoryEntity as response.
     * @throws NameAlreadyExistsException If the name is already in use.
     */
    @Override
    public CategoryEntity saveCategory(final CategoryRequestDTO categoryRequestDTO) {
        log.info("Executing saveCategory from CategoryServiceImpl");

        CategoryEntity category = create(categoryRequestDTO);

        if (categoryRepository.existsCategoryByName(category.getName()))
            throw new NameAlreadyExistsException("Busy name.");

        return categoryRepository.save(category);
    }

    /**
     * Update an existent category from DTO into the database using JPA.
     *
     * @param id       Long id, to search the specific category to update.
     * @param toUpdate CategoryDTO as request.
     * @return CategoryEntity as response.
     * @throws EntityNotFoundException    If the category doesn't exist.
     * @throws NameAlreadyExistsException If the name is already in use.
     */
    @Override
    public CategoryEntity updateCategory(final Long id, final CategoryRequestDTO toUpdate) {
        log.info("Executing updateCategory from CategoryServiceImpl");

        CategoryEntity category = categoryRepository.findCategoryById(id);

        if (category == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    CategoryEntity.class.getSimpleName() + " with id: " + id);
        }

        update(category, toUpdate);

        return categoryRepository.save(category);
    }

    /**
     * Delete a category into the database using JPA.
     *
     * @param id Long id, to search and delete the specific category.
     * @return CategoryEntity as response.
     * @throws EntityNotFoundException If the category doesn't exist.
     */
    @Override
    public CategoryEntity deleteCategory(final Long id) {
        log.info("Executing deleteCategory from CategoryServiceImpl");

        CategoryEntity category = categoryRepository.findCategoryById(id);

        if (category == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    CategoryEntity.class.getSimpleName() + " with id: " + id);
        }

        categoryRepository.deleteById(id);

        return category;
    }

    /**
     * Find all the categories from the database using JPA.
     *
     * @return Lis<CategoryEntity> it can be Null if there isn't categories.
     */
    @Override
    public List<CategoryEntity> findAllCategories() {
        log.info("Executing findAllCategories from CategoryServiceImpl");

        return categoryRepository.findAll();
    }

    /**
     * Find a category from the database using JPA.
     *
     * @param id Long id, to search the specific category.
     * @return CategoryEntity as response.
     * @throws EntityNotFoundException If the category doesn't exist.
     */
    @Override
    public CategoryEntity findCategoryById(final Long id) {
        log.info("Executing findCategoryById from CategoryServiceImpl");

        CategoryEntity category = categoryRepository.findCategoryById(id);

        if (category == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    CategoryEntity.class.getSimpleName() + " with id: " + id);
        }

        return category;
    }

    /**
     * Find a category from the database using JPA.
     *
     * @param name String name, to search the specific category.
     * @return CategoryEntity as response.
     * @throws EntityNotFoundException If the category doesn't exist.
     */
    @Override
    public CategoryEntity findCategoryByName(final String name) {
        log.info("Executing findCategoryByName from CategoryServiceImpl");

        CategoryEntity category = categoryRepository.findCategoryByName(name);

        if (category == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    CategoryEntity.class.getSimpleName() + " with name: " + name);
        }

        return category;
    }

    /**
     * Create a category from DTO.
     *
     * @param categoryRequestDTO CategoryDTO.
     * @return CategoryEntity.
     */
    private CategoryEntity create(CategoryRequestDTO categoryRequestDTO) {
        return CategoryEntity
                .builder()
                .name(categoryRequestDTO.getName())
                .description(categoryRequestDTO.getDescription())
                .build();
    }

    /**
     * Update an existent category from DTO.
     *
     * @param category           CategoryEntity.
     * @param categoryRequestDTO CategoryDTO.
     * @void
     */
    private void update(CategoryEntity category, CategoryRequestDTO categoryRequestDTO) {
        if (category.getName().equals(categoryRequestDTO.getName())) {
            category.setName(categoryRequestDTO.getName());
        } else {
            if (categoryRepository.existsCategoryByName(categoryRequestDTO.getName()))
                throw new NameAlreadyExistsException("Busy name.");

            category.setName(categoryRequestDTO.getName());
        }

        category.setDescription(categoryRequestDTO.getDescription());
    }

}
