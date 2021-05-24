package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "creator")
@Table(name = "PARKS", schema="soprafs21")
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Point coordinate;

    @Column
    private String description;

    @JoinColumn
    @ManyToOne
    private User creator;
}
