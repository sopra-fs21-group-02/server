package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @JoinColumn(nullable = false)
    @ManyToOne
    @JsonIgnore
    private User creator;

    @Column
    private String description;

}
