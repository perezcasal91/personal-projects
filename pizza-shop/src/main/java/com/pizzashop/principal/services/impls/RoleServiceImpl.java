package com.pizzashop.principal.services.impls;

import com.pizzashop.principal.dtos.RoleRequestDTO;
import com.pizzashop.principal.entities.RoleEntity;
import com.pizzashop.principal.exceptions.EntityNotFoundException;
import com.pizzashop.principal.exceptions.NameAlreadyExistsException;
import com.pizzashop.principal.repositories.RoleRepository;
import com.pizzashop.principal.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Save a role from DTO into the database using JPA.
     *
     * @param roleRequestDTO RoleDTO as request.
     * @return RoleEntity as response.
     * @throws NameAlreadyExistsException If the name is already in use.
     */
    @Override
    public RoleEntity saveRole(final RoleRequestDTO roleRequestDTO) {
        log.info("Executing saveRole from RoleServiceImpl");

        RoleEntity role = create(roleRequestDTO);

        if (roleRepository.existsRoleByName(role.getName()))
            throw new NameAlreadyExistsException("Busy name.");

        return roleRepository.save(role);
    }

    /**
     * Update an existent role from DTO into the database using JPA.
     *
     * @param id       Long id, to search the specific role to update.
     * @param toUpdate RoleDTO as request.
     * @return RoleEntity as response.
     * @throws EntityNotFoundException    If the role doesn't exist.
     * @throws NameAlreadyExistsException If the name is already in use.
     */
    @Override
    public RoleEntity updateRole(final Long id, final RoleRequestDTO toUpdate) {
        log.info("Executing updateRole from RoleServiceImpl");

        RoleEntity role = roleRepository.findRoleById(id);

        if (role == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    RoleEntity.class.getSimpleName() + " with id: " + id);
        }

        update(role, toUpdate);

        return roleRepository.save(role);
    }

    /**
     * Delete a role into the database using JPA.
     *
     * @param id Long id, to search and delete the specific role.
     * @return RoleEntity as response.
     * @throws EntityNotFoundException If the role doesn't exist.
     */
    @Override
    public RoleEntity deleteRole(final Long id) {
        log.info("Executing deleteRole from RoleServiceImpl");

        RoleEntity role = roleRepository.findRoleById(id);

        if (role == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    RoleEntity.class.getSimpleName() + " with id: " + id);
        }

        roleRepository.deleteById(id);

        return role;
    }

    /**
     * Find all the roles from the database using JPA.
     *
     * @return Lis<RoleEntity> it can be Null if there isn't roles.
     */
    @Override
    public List<RoleEntity> findAllRoles() {
        log.info("Executing findAllRoles from RoleServiceImpl");

        return roleRepository.findAll();
    }

    /**
     * Find a role from the database using JPA.
     *
     * @param id Long id, to search the specific role.
     * @return RoleEntity as response.
     * @throws EntityNotFoundException If the role doesn't exist.
     */
    @Override
    public RoleEntity findRoleById(final Long id) {
        log.info("Executing findRoleById from RoleServiceImpl");

        RoleEntity role = roleRepository.findRoleById(id);

        if (role == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    RoleEntity.class.getSimpleName() + " with id: " + id);
        }

        return role;
    }

    /**
     * Find a role from the database using JPA.
     *
     * @param name String name, to search the specific role.
     * @return RoleEntity as response.
     * @throws EntityNotFoundException If the role doesn't exist.
     */
    @Override
    public RoleEntity findRoleByName(final String name) {
        log.info("Executing findRoleByName from RoleServiceImpl");

        RoleEntity role = roleRepository.findRoleByName(name);

        if (role == null) {
            throw new EntityNotFoundException("Couldn't find " +
                    RoleEntity.class.getSimpleName() + " with name: " + name);
        }

        return role;
    }

    /**
     * Create a role from DTO.
     *
     * @param roleRequestDTO RoleDTO.
     * @return RoleEntity.
     */
    private RoleEntity create(RoleRequestDTO roleRequestDTO) {
        return RoleEntity
                .builder()
                .name(roleRequestDTO.getName())
                .description(roleRequestDTO.getDescription())
                .build();
    }

    /**
     * Update an existent role from DTO.
     *
     * @param role           RoleEntity.
     * @param roleRequestDTO RoleDTO.
     * @void
     */
    private void update(RoleEntity role, RoleRequestDTO roleRequestDTO) {
        if (role.getName().equals(roleRequestDTO.getName())) {
            role.setName(roleRequestDTO.getName());
        } else {
            if (roleRepository.existsRoleByName(roleRequestDTO.getName()))
                throw new NameAlreadyExistsException("Busy name.");

            role.setName(roleRequestDTO.getName());
        }

        role.setDescription(roleRequestDTO.getDescription());
    }

}
