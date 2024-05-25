package org.pao.audiolibrarypao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("name")
    @ToString.Exclude
    @Getter
    private String name;

    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @Column(nullable = false)
    @Getter
    private boolean admin;

    @CreationTimestamp private LocalDateTime createdAt;
}
