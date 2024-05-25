package org.pao.audiolibrarypao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.pao.audiolibrarypao.utils.classes.View;

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

    @JsonView(View.Public.class)
    @Column(nullable = false)
    @Getter
    private String name;

    @Getter
    @Column(nullable = false, unique = true)
    @JsonView(View.Public.class)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @JsonView(View.Internal.class)
    @Getter
    @Setter
    private boolean admin;


    @CreationTimestamp private LocalDateTime createdAt;
}
