package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private static final String CLIENT_ID = "1057742566572-4ufig26uc1s8tiggp6ja3tf13s4iuo87.apps.googleusercontent.com";

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(OnlineStatus.OFFLINE);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }*/



    /**
     * Helper class to authenticate tokenId passed from client with Google
     * @param tokenId
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public GoogleIdToken authenticateTokenId(String tokenId) throws GeneralSecurityException, IOException {
        JsonFactory factory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), factory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        return verifier.verify(tokenId);
    }

    /**
     *
     * @param tokenId
     * @param emailId
     * @return
     * @throws ResponseStatusException
     */
    public Boolean verifyEmailIdForToken(GoogleIdToken tokenId, String emailId) throws ResponseStatusException{
        if (null!=tokenId && null!= emailId) {

            GoogleIdToken.Payload payload = tokenId.getPayload();
            if (emailId.equals(payload.getEmail())) {
                return Boolean.TRUE;
            }else{
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Invalid Token");
            }
        }
        return Boolean.FALSE;
    }

    public boolean loginOrRegisterUser(GoogleIdToken.Payload payload){
        boolean isNewUser = false;
        User user = userRepository.findByEmail(payload.getEmail());

        if(user==null){
            user = new User();
            user.setProviderUid(payload.getSubject());
            user.setProvider(payload.getIssuer());
            user.setName((String) payload.get("name"));
            user.setProfilePictureURL((String) payload.get("picture"));
            user.setEmail(payload.getEmail());
            user.setStatus(OnlineStatus.ONLINE);
            user.setToken(payload.getAccessTokenHash());
            isNewUser = true;
            userRepository.saveAndFlush(user);
        }else{
            user.setStatus(OnlineStatus.ONLINE);
            userRepository.flush();
        }
        return isNewUser;
    }

}
