package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.TagType;
import lombok.*;

import javax.persistence.*;

/**
 * Internal Tag representation
 */
@Data
@EqualsAndHashCode(exclude="owner")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TAGS", schema="soprafs21")
public class  Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private TagType tagType;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private User owner;
 }

