package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IMAGES", schema="soprafs21")
public class Image {

    @Id
    @GeneratedValue
    Long id;

    @Lob
    byte[] content;

    String name;
    // Getters and Setters

}