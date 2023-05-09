package com.pizzashop.principal.services.impls;

import com.pizzashop.principal.dtos.ProductRequestDTO;
import com.pizzashop.principal.entities.CategoryEntity;
import com.pizzashop.principal.entities.ProductEntity;
import com.pizzashop.principal.entities.ProductStatus;
import com.pizzashop.principal.exceptions.EntityNotFoundException;
import com.pizzashop.principal.exceptions.NameAlreadyExistsException;
import com.pizzashop.principal.repositories.CategoryRepository;
import com.pizzashop.principal.repositories.ProductRepository;
import com.pizzashop.principal.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Save a product from DTO into the database using JPA.
     *
     * @param productRequestDTO ProductDTO as request.
     * @return ProductEntity as response.
     * @throws EntityNotFoundException    If the category doesn't exist.
     * @throws NameAlreadyExistsException If the name is already in use.
     */
    @Override
    public ProductEntity saveProduct(final ProductRequestDTO productRequestDTO) {
        log.info("Executing saveProduct from ProductServiceImpl");

        CategoryEntity category = categoryRepository
                .findCategoryById(productRequestDTO.getCategoryId());

        if (category != null) {

            ProductEntity product = create(productRequestDTO, category);

            if (productRepository.existsProductByName(productRequestDTO.getName()))
                throw new NameAlreadyExistsException("Busy name.");

            product = productRepository.save(product);

            return product;
        } else {
            throw new EntityNotFoundException("Couldn't find " +
                    CategoryEntity.class.getSimpleName() + " with id: " +
                    productRequestDTO.getCategoryId());
        }
    }

    /**
     * Update an existent product from DTO into the database using JPA.
     * If the categoryId is equal to 0, it means the category doesn't change.
     *
     * @param id       Long id, to search the specific product to update.
     * @param toUpdate ProductDTO as request.
     * @return ProductEntity as response.
     * @throws EntityNotFoundException    If the product or the category doesn't exist.
     * @throws NameAlreadyExistsException If the name is already in use.
     */
    @Override
    public ProductEntity updateProduct(final Long id, final ProductRequestDTO toUpdate) {
        log.info("Executing updateProduct from ProductServiceImpl");

        ProductEntity product = productRepository.findProductById(id);

        if (product != null) {

            update(product, toUpdate);

            product = productRepository.save(product);

            return product;
        } else {
            throw new EntityNotFoundException("Couldn't find " +
                    ProductEntity.class.getSimpleName() + " with id: " + id);
        }
    }

    /**
     * Delete a product into the database using JPA.
     *
     * @param id Long id, to search and delete the specific product.
     * @return ProductEntity as response.
     * @throws EntityNotFoundException If the product doesn't exist.
     */
    @Override
    public ProductEntity deleteProduct(final Long id) {
        log.info("Executing deleteProduct from ProductServiceImpl");

        ProductEntity product = productRepository.findProductById(id);

        if (product != null) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Couldn't find " +
                    ProductEntity.class.getSimpleName() + " with id: " + id);
        }

        return product;
    }

    /**
     * Find all the products from the database using JPA.
     *
     * @return Lis<ProductEntity> it can be Null if there isn't products.
     */
    @Override
    public List<ProductEntity> findAllProducts() {
        log.info("Executing findAllProducts from ProductServiceImpl");

        return productRepository.findAll();
    }

    /**
     * Find a product from the database using JPA.
     *
     * @param id Long id, to search the specific product.
     * @return ProductEntity as response.
     * @throws EntityNotFoundException If the product doesn't exist.
     */
    @Override
    public ProductEntity findProductById(final Long id) {
        log.info("Executing findProductById from ProductServiceImpl");

        ProductEntity product = productRepository.findProductById(id);

        if (product == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    ProductEntity.class.getSimpleName() + " with id: " + id);
        }

        return product;
    }

    /**
     * Find products from the database using JPA.
     *
     * @param name String name, to search the specific product.
     * @return ProductEntity as response.
     * @throws EntityNotFoundException If the product doesn't exist.
     */
    @Override
    public ProductEntity findProductByName(final String name) {
        log.info("Executing findProductByName from ProductServiceImpl");

        ProductEntity product = productRepository.findProductByName(name);

        if (product == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    ProductEntity.class.getSimpleName() + " with name: " + name);
        }

        return product;
    }

    /**
     * Find a product from the database using JPA.
     *
     * @param status String statusValue, to search the specific products.
     * @return List<ProductEntity> it can be Null if there isn't products with that statusValue.
     */
    @Override
    public List<ProductEntity> findProductsByStatus(final String status) {
        log.info("Executing findProductsByStatus from ProductServiceImpl");

        return productRepository.findProductsByStatus(ProductStatus.valueOf(status));
    }

    @Override
    public List<ProductEntity> findProductsByCategoryId(Long categoryId) {
        log.info("Executing findProductsByCategory from ProductServiceImpl");

        return productRepository.findProductsByCategoryId(categoryId);
    }

    /**
     * Create a product from DTO.
     *
     * @param productRequestDTO ProductDTO.
     * @return ProductEntity.
     */
    private ProductEntity create(ProductRequestDTO productRequestDTO,
                                 CategoryEntity category) {
        return ProductEntity
                .builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .amount(productRequestDTO.getAmount())
                .price(productRequestDTO.getPrice())
                .imageUrl(productRequestDTO.getImageUrl())
                .status(ProductStatus.valueOf(productRequestDTO.getStatus()))
                .category(category)
                .build();
    }

    /**
     * Update an existent product from DTO.
     *
     * @param product           ProductEntity.
     * @param productRequestDTO ProductDTO.
     * @void
     */
    private void update(ProductEntity product, ProductRequestDTO productRequestDTO) {
        if (product.getName().equals(productRequestDTO.getName())) {
            product.setName(productRequestDTO.getName());
        } else {
            if (productRepository.existsProductByName(productRequestDTO.getName()))
                throw new NameAlreadyExistsException("Busy name.");

            product.setName(productRequestDTO.getName());
        }

        product.setDescription(productRequestDTO.getDescription());
        product.setAmount(productRequestDTO.getAmount());
        product.setPrice(productRequestDTO.getPrice());
        product.setImageUrl(productRequestDTO.getImageUrl());
        product.setStatus(ProductStatus.valueOf(productRequestDTO.getStatus()));

        CategoryEntity category = categoryRepository
                .findCategoryById(productRequestDTO.getCategoryId());

        if (productRequestDTO.getCategoryId() != 0) {
            if (category != null) {
                product.setCategory(category);
            } else {
                throw new EntityNotFoundException("Couldn't find " +
                        CategoryEntity.class.getSimpleName() + " with id: " +
                        productRequestDTO.getCategoryId());
            }
        }
    }

}
