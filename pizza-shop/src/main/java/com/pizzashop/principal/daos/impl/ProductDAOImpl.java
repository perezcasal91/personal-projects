package com.pizzashop.principal.daos.impl;

import com.pizzashop.principal.daos.ProductDAO;
import com.pizzashop.principal.entities.ProductEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private Session session;

    @Override
    public List<ProductEntity> mostSellProducts() {
        String query = "select distinct p " +
                "from " + ProductEntity.class.getName() + " p " +
                "where p.price";

        return session.createQuery(query, ProductEntity.class)
                .list();
    }


}
