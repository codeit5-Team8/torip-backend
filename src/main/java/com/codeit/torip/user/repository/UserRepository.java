package com.codeit.torip.user.repository;

import com.codeit.torip.user.entity.OauthPlatform;
import com.codeit.torip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndOauthPlatform(String email, OauthPlatform oauthPlatform);

    Optional<User>findUserByEmail(String email);

    Optional<User> findUserById(Long userId);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);
}
