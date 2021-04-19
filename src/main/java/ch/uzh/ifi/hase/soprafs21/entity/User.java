package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


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
@Table(name = "USERS", schema="soprafs21")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Column(nullable = true)
    private Date dateOfBirth;

    //Get from ExternalLogin = OAuth provider
    @Column(nullable = false)
    private String provider;

    //Get from ExternalLogin = OAuth external user ID
    @Column(nullable = false)
    private String providerUid;

    @Column(nullable = true, unique = true)
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OnlineStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String bio;

    @OneToMany
    @JoinColumn
    private List<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Dog> listOfDogs;

    @Column
    private Point lastUserLocation;
}