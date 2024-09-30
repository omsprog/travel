package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.exception.UsernameNotFoundException;
import com.omsprog.travel.repository.AppUserRepository;
import com.omsprog.travel.service.abstract_service.IModifyUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class AppUserService implements IModifyUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public Map<String, Boolean> enabled(String username) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.setEnabled(!user.isEnabled());
        var userSaved = this.appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(), userSaved.isEnabled());
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = this.appUserRepository.save(user);
        var authorities = user.getRole().getGrantedAuthorities();
        log.info("User {} add role {}", userSaved.getUsername(), userSaved.getRole().getGrantedAuthorities().toString());
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = this.appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        log.info("User {} remove role {}", userSaved.getUsername(), userSaved.getRole().getGrantedAuthorities().toString());
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }
}
