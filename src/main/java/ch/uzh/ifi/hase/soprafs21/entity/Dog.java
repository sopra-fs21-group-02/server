package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Internal Dog representation
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "dogs_without_picture_and_owner",
        includeAllAttributes = true,
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("name"),
            @NamedAttributeNode("breed"),
            @NamedAttributeNode("dateOfBirth"),
            @NamedAttributeNode("gender")

        }
)
@Table(name = "DOGS", schema="soprafs21")
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private User owner;
   }
