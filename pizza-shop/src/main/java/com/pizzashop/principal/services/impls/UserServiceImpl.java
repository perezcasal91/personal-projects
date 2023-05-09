package com.pizzashop.principal.services.impls;

import com.pizzashop.principal.dtos.UserRequestDTO;
import com.pizzashop.principal.entities.RoleEntity;
import com.pizzashop.principal.entities.UserEntity;
import com.pizzashop.principal.exceptions.EmailAlreadyExistsException;
import com.pizzashop.principal.exceptions.EntityNotFoundException;
import com.pizzashop.principal.exceptions.UsernameAlreadyExistsException;
import com.pizzashop.principal.repositories.UserRepository;
import com.pizzashop.principal.services.RoleService;
import com.pizzashop.principal.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Save a user from DTO into the database using JPA.
     * It uses the PasswordEncode Bean to encode the password.
     *
     * @param userRequestDTO UserDTO as request.
     * @return UserEntity as response.
     * @throws UsernameAlreadyExistsException If the username is already in use.
     * @throws EmailAlreadyExistsException    If the email is already in use.
     */
    @Override
    public UserEntity saveUser(final UserRequestDTO userRequestDTO) {
        log.info("Executing saveUser from UserServiceImpl");

        UserEntity user = create(userRequestDTO);

        if (userRepository.existsUserByUsername(user.getUsername()))
            throw new UsernameAlreadyExistsException("Busy username.");

        if (userRepository.existsUserByEmail(user.getEmail()))
            throw new EmailAlreadyExistsException("Busy email.");

        user = userRepository.save(user);

        return user;
    }

    /**
     * Update an existent user from DTO into the database using JPA.
     *
     * @param id       Long id, to search the specific user to update.
     * @param toUpdate UserDTO as request.
     * @return UserEntity as response.
     * @throws UsernameAlreadyExistsException If the username is already in use.
     * @throws EmailAlreadyExistsException    If the email is already in use.
     * @throws EntityNotFoundException        If the user doesn't exist.
     */
    @Override
    public UserEntity updateUser(final Long id, final UserRequestDTO toUpdate) {
        log.info("Executing updateUser from UserServiceImpl");

        UserEntity user = userRepository.findUserById(id);

        if (user == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    UserEntity.class.getSimpleName() + " with id: " + id);
        }

        update(user, toUpdate);

        return userRepository.save(user);
    }

    /**
     * Delete a user into the database using JPA.
     *
     * @param id Long id, to search and delete the specific user.
     * @return UserEntity as response.
     * @throws EntityNotFoundException If the user doesn't exist.
     */
    @Override
    public UserEntity deleteUser(final Long id) {
        log.info("Executing deleteUser from UserServiceImpl");

        UserEntity user = userRepository.findUserById(id);

        if (user == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    UserEntity.class.getSimpleName() + " with id: " + id);
        }

        userRepository.deleteById(id);

        return user;
    }

    /**
     * Find all the users from the database using JPA.
     *
     * @return Lis<UserEntity> it can be Null if there isn't users.
     */
    @Override
    public List<UserEntity> findAllUsers() {
        log.info("Executing findAllUsers from UserServiceImpl");

        return userRepository.findAll();
    }

    /**
     * Find a user from the database using JPA.
     *
     * @param id Long id, to search the specific user.
     * @return UserEntity as response.
     * @throws EntityNotFoundException If the user doesn't exist.
     */
    @Override
    public UserEntity findUserById(final Long id) {
        log.info("Executing findUserById from UserServiceImpl");

        UserEntity user = userRepository.findUserById(id);

        if (user == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    UserEntity.class.getSimpleName() + " with id: " + id);
        }

        return user;
    }

    /**
     * Find a user from the database using JPA.
     *
     * @param username String username, to search the specific user.
     * @return UserEntity as response.
     * @throws EntityNotFoundException If the user doesn't exist.
     */
    @Override
    public UserEntity findUserByUsername(final String username) {
        log.info("Executing findUserByUsername from UserServiceImpl");

        UserEntity user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    UserEntity.class.getSimpleName() + " with username: " + username);
        }

        return user;
    }

    /**
     * Find a user from the database using JPA.
     *
     * @param email String email, to search the specific user.
     * @return UserEntity as response.
     * @throws EntityNotFoundException If the user doesn't exist.
     */
    @Override
    public UserEntity findUserByEmail(final String email) {
        log.info("Executing findUserByEmail from UserServiceImpl");

        UserEntity user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    UserEntity.class.getSimpleName() + " with email: " + email);
        }

        return user;
    }

    /**
     * Create a user from DTO.
     *
     * @param userRequestDTO UserDTO.
     * @return UserEntity.
     */
    private UserEntity create(UserRequestDTO userRequestDTO) {
        Set<RoleEntity> roles = new HashSet<>();

        Arrays.stream(userRequestDTO.getRoles().split(","))
                .forEach(
                        r -> {
                            if (r != null)
                                roles.add(roleService.findRoleByName(r));
                        }
                );

        return UserEntity
                .builder()
                .firstName(userRequestDTO.getFirstName())
                .middleName(userRequestDTO.getMiddleName())
                .lastName(userRequestDTO.getLastName())
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .phone(userRequestDTO.getPhone())
                .roles(roles)
                .build();
    }

    /**
     * Update an existent user from DTO.
     *
     * @param user           UserEntity.
     * @param userRequestDTO UserDTO.
     * @void
     */
    private void update(UserEntity user, UserRequestDTO userRequestDTO) {
        user.setFirstName(userRequestDTO.getFirstName());
        user.setMiddleName(userRequestDTO.getMiddleName());
        user.setLastName(userRequestDTO.getLastName());

        if (user.getUsername().equals(userRequestDTO.getUsername())) {
            user.setUsername(userRequestDTO.getUsername());
        } else {
            if (userRepository.existsUserByUsername(userRequestDTO.getUsername()))
                throw new UsernameAlreadyExistsException("Busy username.");

            user.setUsername(userRequestDTO.getUsername());
        }

        if (user.getEmail().equals(userRequestDTO.getEmail())) {
            user.setEmail(userRequestDTO.getEmail());
        } else {
            if (userRepository.existsUserByEmail(userRequestDTO.getEmail()))
                throw new EmailAlreadyExistsException("Busy email.");

            user.setEmail(userRequestDTO.getEmail());
        }

        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPhone(userRequestDTO.getPhone());

        Set<RoleEntity> roles = new HashSet<>();

        Arrays.stream(userRequestDTO.getRoles().split(","))
                .forEach(
                        r -> {
                            if (r != null)
                                roles.add(roleService.findRoleByName(r));
                        }
                );

        user.setRoles(roles);
    }

}
