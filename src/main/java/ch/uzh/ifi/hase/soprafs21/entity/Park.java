package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PARKS", schema="soprafs21")
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Point coordinate;

    @JoinColumn(nullable = false)
    @ManyToOne
    @JsonIgnore
    private User creator;
}
