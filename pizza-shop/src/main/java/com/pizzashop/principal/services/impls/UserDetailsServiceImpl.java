package com.pizzashop.principal.services.impls;


import com.pizzashop.principal.entities.UserEntity;
import com.pizzashop.principal.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Create new UserDetails from the database user.
     *
     * @param username String username, to search the specific User.
     * @return UserDetails.
     * @throws UsernameNotFoundException If the username is invalid.
     */
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        log.info("Executing loadUserByUsername from UserDetailsServiceImpl");

        UserEntity userEntity = userRepository.findUserByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Invalid username.");
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), getAuthority(userEntity));
    }

    /**
     * Return the authorities from the User.
     *
     * @param userEntity UserEntity.
     * @return Set<SimpleGrantedAuthority> it can be Null if the user doesn't have roles assigned.
     */
    private Set<SimpleGrantedAuthority> getAuthority(UserEntity userEntity) {
        log.info("Executing getAuthority from UserDetailsServiceImpl");

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        userEntity.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()))
        );

        return authorities;
    }

}
