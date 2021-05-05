package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE ChatMessage c SET c.unread = false WHERE c.conversation = ?1 and c.unread = true")
    void setMessagesReadByConversation(Conversation conversation);

    @Transactional
    @Modifying
    @Query("Delete from ChatMessage c WHERE c.sender =?1 or c.receiver =?1")
    void removeUserFromMessages(User userToRemove);
}
