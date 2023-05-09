package com.pizzashop.principal.services;

import com.pizzashop.principal.dtos.UserRequestDTO;
import com.pizzashop.principal.entities.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity saveUser(UserRequestDTO userRequestDTO);

    UserEntity updateUser(Long id, UserRequestDTO toUpdate);

    UserEntity deleteUser(Long id);

    List<UserEntity> findAllUsers();

    UserEntity findUserById(Long id);

    UserEntity findUserByUsername(String username);

    UserEntity findUserByEmail(String email);
}
