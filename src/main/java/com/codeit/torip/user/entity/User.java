package com.codeit.torip.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Table(name = "users")
@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 30, unique = true)
    private String username;
    @Enumerated(EnumType.STRING)
    private OauthPlatform oauthPlatform;

    @Builder
    public User(String email, String password, String username, OauthPlatform oauthPlatform) {
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.username = Objects.requireNonNull(username);
        this.oauthPlatform = Objects.requireNonNullElse(oauthPlatform, OauthPlatform.NONE);
    }


}
