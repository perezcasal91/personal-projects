package com.pizzashop.principal.daos.impl;

import com.pizzashop.principal.daos.CategoryDAO;
import com.pizzashop.principal.entities.CategoryEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private Session session;

    @Override
    public CategoryEntity findCategoryWithProductsById(Long id) {
        String query = "select distinct c " +
                "from " + CategoryEntity.class.getName() + " c " +
                "join fetch c.products " +
                "where c.id=:id";

        return session.createQuery(query, CategoryEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
