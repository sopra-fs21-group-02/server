package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.ChatMessageRepository;
import ch.uzh.ifi.hase.soprafs21.repository.ConversationRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    private final ConversationRepository conversationRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository, ConversationRepository conversationRepository, UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.conversationRepository = conversationRepository;
        this.userService = userService;
    }
    /**
     * Returns a list of all conversations by user
     * @param senderId id of user to get all conversation
     * @return list of conversations
     */
    public List<Conversation> getAllConversations(Long senderId){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(senderId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return conversationRepository.findByUser(getCurrentlyLoggedinUser().orElseThrow());
    }

    /**
     * Returns a list of messages between two participants
     * @param participant1 the first participant of a conversation
     * @param participant2 the second participant of a conversation
     * @return list of messages
     */
    public List<ChatMessage> getAllMessages(User participant1, User participant2) {
        if (!userService.isRequesterAndAuthenticatedUserTheSame(participant1.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Conversation currentConversation = findConversationByParticipants(participant2).orElseThrow();
        List<ChatMessage> allMessages = currentConversation.getMessages();
        chatMessageRepository.setMessagesReadByConversation(currentConversation);
        return allMessages;
    }

    /**
     * Searches for a conversation by user
     * @param user2 the second participant of a conversation
     * @return conversation or empty Optional instance
     */
    private Optional<Conversation> findConversationByParticipants(User user2) {
        return conversationRepository.findByParticipant1AndParticipant2(getCurrentlyLoggedinUser().orElseThrow(), user2);
    }

    /**
     * Creates a new message between two users
     * @param participant1 the sender of the message
     * @param participant2 the receiver of the message
     * @param message text of the message
     */
    @Transactional
    public void createMessage(User participant1, User participant2, String message) {
        if (!userService.isRequesterAndAuthenticatedUserTheSame(participant1.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Optional<Conversation> optionalConversation = findConversationByParticipants(participant2);
        Conversation conversation;
        if(optionalConversation.isEmpty()) {
            conversation = createNewConversation(participant2);
        } else {
            conversation = optionalConversation.get();
        }
        ChatMessage newMessage = ChatMessage.builder()
        .message(message)
        .sender(getCurrentlyLoggedinUser().orElseThrow())
        .receiver(participant2)
        .conversation(conversation).build();
        chatMessageRepository.saveAndFlush(newMessage);
    }

    /**
     * Creates a new Conversation between authenticated user and provided user
     * @param participant2 the second participant of conversation
     * @return conversation between two users
     */
    private Conversation createNewConversation(User participant2){
        Conversation newConversation = new Conversation();
        newConversation.setParticipant1(getCurrentlyLoggedinUser().orElseThrow());
        newConversation.setParticipant2(participant2);
        return conversationRepository.saveAndFlush(newConversation);
    }

    /**
     * Returns currently authenticated user
     * @return authenticated user or empty Optional instance
     */
    private Optional<User> getCurrentlyLoggedinUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = ((UserOverviewDto)auth.getPrincipal()).getEmail();
            return userRepository.findByEmail(currentUserEmail);
        } else {
            return Optional.empty();
        }
    }

}
