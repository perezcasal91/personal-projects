package com.pizzashop.principal.configs;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JPAConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * Extract the Session from the Entity Manager.
     *
     * @return Session.
     */
    @Bean
    public Session getSession() {
        return this.entityManagerFactory
                .unwrap(SessionFactory.class).openSession();
    }

}
