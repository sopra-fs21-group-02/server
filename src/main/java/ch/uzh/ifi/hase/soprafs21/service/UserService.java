package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginPostDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.UserDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import io.jsonwebtoken.Claims;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZoneOffset;
import java.util.*;

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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Environment env;

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
                .setAudience(Collections.singletonList(env.getProperty("spring.security.oauth2.client.registration.google.clientId")))
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

    public boolean loginOrRegisterUser(GoogleIdToken.Payload payload, String refreshToken){
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
            .token(refreshToken).build();
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
        boolean isSame =Boolean.FALSE;
        UserOverviewDto userDetails = (UserOverviewDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails != null && senderId !=null){
            isSame = senderId.equals(userDetails.getId());
        }
        return isSame;
    }

    @Transactional
    public void updateUserLocation(Long userId, Point newLocation) {
        this.userRepository.updateUserLocation(userId, newLocation);
    }

    public String refreshToken(String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken, SecurityConstants.REFRESH_SECRET)) {
            Claims claims = jwtTokenUtil.getClaimsFromJWT(refreshToken, SecurityConstants.REFRESH_SECRET);
            String emailId = claims.getSubject();
            Optional<User> optionalUser = userRepository.findByEmail(emailId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getToken().equals(refreshToken)) {
                    return emailId;
                }
                else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }
       return null;

    }

    @Transactional
    public void updateRefreshTokenForUser(String newRefreshToken, String emailId) {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setToken(newRefreshToken);
            userRepository.saveAndFlush(user);
        }
    }

    @Transactional
    public void logoutUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setToken(null);
            user.setStatus(OnlineStatus.OFFLINE);
            userRepository.saveAndFlush(user);
        }
    }

    public List<User> getAllUsersInArea(Polygon areaFilterPolygon){
        return this.userRepository.findByArea(areaFilterPolygon);
    }

    public User getUserDetails(Long userId) {
        User user = null;
        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }
}
