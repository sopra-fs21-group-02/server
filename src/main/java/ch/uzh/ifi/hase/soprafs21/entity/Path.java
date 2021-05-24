package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "creator")
@Entity
@Table(name = "PATHS", schema="soprafs21")
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LineString route;

    @Column
    private Double distance;

    @JoinColumn
    @ManyToOne
    @JsonIgnore
    private User creator;

    @Column
    private String description;

}
