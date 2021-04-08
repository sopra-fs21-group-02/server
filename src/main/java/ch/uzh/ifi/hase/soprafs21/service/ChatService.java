package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.ChatMessageRepository;
import ch.uzh.ifi.hase.soprafs21.repository.ConversationRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public List<Conversation> getAllConversations(Long senderId){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(senderId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return conversationRepository.findByUser(getCurrentlyLoggedinUser().orElseThrow());
    }

    public List<ChatMessage> getAllMessages(User participant1, User participant2) {
        if (!userService.isRequesterAndAuthenticatedUserTheSame(participant1.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Conversation currentConversation = findConversationByParticipants(participant2).orElseThrow();
        List<ChatMessage> allMessages = currentConversation.getMessages();
        chatMessageRepository.setMessagesReadByConversation(currentConversation);
        return allMessages;
    }

    private Optional<Conversation> findConversationByParticipants(User user2) {
        return conversationRepository.findByParticipant1AndParticipant2(getCurrentlyLoggedinUser().orElseThrow(), user2);
    }

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

    private Conversation createNewConversation(User participant2){
        Conversation newConversation = new Conversation();
        newConversation.setParticipant1(getCurrentlyLoggedinUser().orElseThrow());
        newConversation.setParticipant2(participant2);
        return conversationRepository.saveAndFlush(newConversation);
    }

    private Optional<User> getCurrentlyLoggedinUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = (String) auth.getPrincipal();
            return userRepository.findByEmail(currentUserEmail);
        } else {
            return Optional.empty();
        }
    }

}
