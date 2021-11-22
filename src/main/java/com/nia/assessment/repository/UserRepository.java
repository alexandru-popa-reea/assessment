package com.nia.assessment.repository;

import com.nia.assessment.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByUsernameIn(List<String> usernamesList);
}
