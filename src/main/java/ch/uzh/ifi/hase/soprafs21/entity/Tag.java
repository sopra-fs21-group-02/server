package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.TagType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Internal Tag representation
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "tags_without_owner",
        includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("tagType"),
                @NamedAttributeNode("name"),
        }
)

@Table(name = "TAGS", schema="soprafs21")
public class Tag {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private TagType tagType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User owner;
 }

