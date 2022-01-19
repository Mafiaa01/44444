package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepo;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private  final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findById(long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.get();
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            Role newRole = roles.iterator().next();
            if (newRole.getId() == null) {
                newRole = roleRepo.save(newRole);
            }
            user.setRoles(Collections.singleton(newRole));
        }
        return userRepo.save(user);
    }


    @Override
    @Transactional
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            Role newRole = roles.iterator().next();
            if (newRole.getId() == null) {
                newRole = roleRepo.save(newRole);
            }
            user.setRoles(Collections.singleton(newRole));
        }

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username);
    }
}