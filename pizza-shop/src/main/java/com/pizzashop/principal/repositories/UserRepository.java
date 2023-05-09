package com.pizzashop.principal.repositories;

import com.pizzashop.principal.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserById(Long id);

    UserEntity findUserByUsername(String username);

    UserEntity findUserByEmail(String email);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

}
