package org.pao.audiolibrarypao.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String title;
    public String artist;
    public String album;
    public int duration; // Duration in seconds

    // Constructors, Getters and Setters below...
}