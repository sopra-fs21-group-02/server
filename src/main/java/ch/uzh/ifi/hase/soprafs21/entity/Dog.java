package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Internal Dog representation
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "dogs_without_picture_and_owner",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("name"),
            @NamedAttributeNode("breed"),
            @NamedAttributeNode("dateOfBirth"),
            @NamedAttributeNode("gender")

        }
)
@Table(name = "DOGS", schema="soprafs21")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String breed;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] profilePicture;

    @Column
    private String profilePictureContentType;

    @Column
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private User owner;
   }
