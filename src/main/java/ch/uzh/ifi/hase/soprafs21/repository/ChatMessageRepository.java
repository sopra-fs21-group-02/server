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
    @Query("SELECT m FROM ChatMessage m WHERE m.sender = ?1 and m.timeStamp in " +
            "(select max(m1.timeStamp) from ChatMessage m1 where m1.sender = ?1 GROUP BY m.receiver)")
    List<ChatMessage> findAllLastMessagesBySender(User sender);

    @Transactional
    @Modifying
    @Query("UPDATE ChatMessage c SET c.unread = false WHERE c.conversation = ?1 and c.unread = true")
    void setMessagesReadByConversation(Conversation conversation);


}
