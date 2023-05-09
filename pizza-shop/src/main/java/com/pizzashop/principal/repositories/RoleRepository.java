package com.pizzashop.principal.repositories;

import com.pizzashop.principal.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findRoleById(Long id);

    RoleEntity findRoleByName(String name);

    boolean existsRoleByName(String name);

}
