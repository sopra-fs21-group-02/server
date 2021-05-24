package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedEntityGraph(name = "users_and_dogs_without_picture",
        includeAllAttributes = true,
        attributeNodes = @NamedAttributeNode(value = "listOfDogs", subgraph = "dogs_without_picture_and_owner")
)

@Table(name = "USERS", schema="soprafs21")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Get from ExternalLogin
    @Column(nullable = false)
    private String name;

    //Get from ExternalLogin
    @Column(nullable = false)
    private String email;

    //Get from ExternalLogin
    @Column(nullable = false)
    private String profilePictureURL;

    @Column
    private LocalDate dateOfBirth;

    //Get from ExternalLogin = OAuth provider
    @Column(nullable = false)
    private String provider;

    //Get from ExternalLogin = OAuth external user ID
    @Column(nullable = false)
    private String providerUid;

    @Column(unique = true)
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OnlineStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String bio;

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private Set<Dog> listOfDogs;

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private Set<Tag> tags;

    @OneToMany(mappedBy = "creator")
    private Set<Park> parks;

    @OneToMany(mappedBy = "creator")
    private Set<Path> paths;

    @Column
    private Point lastUserLocation;

    @PreRemove
    private void preRemove() {
        for (Park park : parks){
                park.setCreator(null);
            }
            for (Path path : paths){
                path.setCreator(null);
            }
    }
}