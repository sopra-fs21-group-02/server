package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column
    private LineString route;

    @Column
    private Double distance;

    @JoinColumn
    @ManyToOne
    private User creator;

}
