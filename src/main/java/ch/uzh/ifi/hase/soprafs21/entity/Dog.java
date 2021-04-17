package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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

    /*@OneToOne
    @JoinColumn(name = "image", nullable = false)
    private Image image;*/

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private Gender gender;

    @Column
    private String bio;

    @Column
    private String color;

    @Column
    private Double weight;
   }
