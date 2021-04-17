package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

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

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }


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
        Optional<User> optionalUser = userRepository.findByEmail(payload.getEmail());

        if(optionalUser.isEmpty()){
            User user = User.builder()
            .providerUid(payload.getSubject())
            .provider(payload.getIssuer())
            .name((String) payload.get("name"))
            .profilePictureURL((String) payload.get("picture"))
            .email(payload.getEmail())
            .status(OnlineStatus.ONLINE)
            .token(payload.getAccessTokenHash()).build();
            isNewUser = true;
            userRepository.saveAndFlush(user);
        }else{
            optionalUser.get().setStatus(OnlineStatus.ONLINE);
            userRepository.flush();
        }
        return isNewUser;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isRequesterAndAuthenticatedUserTheSame(Long senderId) {
        String authenticatedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail).orElseThrow();
        User sender = userRepository.getOne(senderId);
        return authenticatedUser.equals(sender);
    }

    @Transactional
    public void updateUserLocation(Long userId, Point newLocation) {
        this.userRepository.updateUserLocation(userId, newLocation);
    }
}
