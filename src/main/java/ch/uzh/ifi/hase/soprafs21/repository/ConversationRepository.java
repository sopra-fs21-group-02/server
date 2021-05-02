package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("Select c from Conversation c where c.participant1 = ?1 or c.participant2 = ?1")
    List<Conversation> findByUser(User user);

    @Query("Select c from Conversation c where (c.participant1 = ?1 and c.participant2 = ?2) or " +
            "(c.participant1 = ?2 and c.participant2 = ?1)")
    Optional<Conversation> findByParticipant1AndParticipant2(User user1, User user2);

    @Transactional
    @Modifying
    @Query("UPDATE Conversation conv SET conv.participant1 = null WHERE conv.participant1 = ?1")
    void removeUserAsParticipant1(User userToRemove);

    @Transactional
    @Modifying
    @Query("UPDATE Conversation conv SET conv.participant2 = null WHERE conv.participant2 = ?1")
    void removeUserAsParticipant2(User userToRemove);

    default void removeUserFromConversations(User userToRemove) {
        removeUserAsParticipant1(userToRemove);
        removeUserAsParticipant2(userToRemove);
    }
}
