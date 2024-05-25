package org.pao.audiolibrarypao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.pao.audiolibrarypao.utils.classes.View;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @JsonView(View.Internal.class)
    private Long id;
    @JsonView(View.Public.class)
    public String title;
    @JsonView(View.Public.class)
    public String artist;
    @JsonView(View.Public.class)
    public String album;
    @JsonView(View.Public.class)
    public int duration;
}
