package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;

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
@Entity
@Table(name = "USER")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
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

    //Get from ExternalLogin
    @Column(nullable = false)
    private Date DateOfBirth;

    //Get from ExternalLogin = OAuth provider
    @Column(nullable = false)
    private String provider;

    //Get from ExternalLogin = OAuth external user ID
    @Column(nullable = false)
    private Long providerUid;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private OnlineStatus status;

    @Column
    private Gender gender;

    @Column
    private String bio;

    /*
    @Column
    private GeoCoordinate latestLocation;
    */

    @OneToMany
    @Column
    private List<Dog> listOfDogs;



}