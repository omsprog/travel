package com.omsprog.dashboard_service.security;

import com.omsprog.dashboard_service.entity.jpa.AppUserEntity;
import com.omsprog.dashboard_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity customer = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

        return new org.springframework.security.core.userdetails.User(
            customer.getEmail(),
            customer.getPassword(),
            true,
            true,
            true,
            true,
            Collections.emptyList() // Authorities
        );
    }
}