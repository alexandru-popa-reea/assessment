package com.nia.assessment.service.impl;

import com.nia.assessment.model.UserEntity;
import com.nia.assessment.repository.UserRepository;
import com.nia.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepo.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepo.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found in the database");
        }
    }
}
