package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CONVERSATIONS", schema="soprafs21")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.DETACH)
    private User participant1;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.DETACH)
    private User participant2;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("timeStamp DESC")
    private List<ChatMessage> messages;
}
