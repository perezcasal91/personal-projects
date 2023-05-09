package com.pizzashop.principal.services;

import com.pizzashop.principal.dtos.RoleRequestDTO;
import com.pizzashop.principal.entities.RoleEntity;

import java.util.List;

public interface RoleService {

    RoleEntity saveRole(RoleRequestDTO roleRequestDTO);

    RoleEntity updateRole(Long id, RoleRequestDTO toUpdate);

    RoleEntity deleteRole(Long id);

    List<RoleEntity> findAllRoles();

    RoleEntity findRoleById(Long id);

    RoleEntity findRoleByName(String name);
}
