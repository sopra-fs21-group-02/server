package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Internal Dog representation
 */
@Data
@Entity
@Table(name = "DOGS", schema="soprafs21")
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String breed;

    @Column
    @Lob
    private byte[] profilePicture;

    @Column
    private Date dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String bio;

    @Column
    private String color;

    @Column
    private Float weight;

    @ManyToOne
    @JoinColumn
    private User owner;
   }
