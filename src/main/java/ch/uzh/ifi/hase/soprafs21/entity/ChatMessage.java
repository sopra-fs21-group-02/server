package ch.uzh.ifi.hase.soprafs21.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MESSAGES", schema="soprafs21")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private User sender;

    @JoinColumn
    @ManyToOne
    private User receiver;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column(nullable = false)
    private boolean unread;

    @JoinColumn(name = "conversation_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Conversation conversation;


    @PrePersist
    private void updateTimeStamp() {
        this.timeStamp = LocalDateTime.now();
        unread = true;
    }


}

