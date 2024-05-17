package org.pao.audiolibrarypao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Setter
    @Getter
    private String token;
    private Long userId;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean revoked;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean expired;
    @CreationTimestamp
    private LocalDateTime createdAt;


    public Token(String jwtToken, User user) {
        this.token = jwtToken;
        this.userId = user.getId();
    }
}
