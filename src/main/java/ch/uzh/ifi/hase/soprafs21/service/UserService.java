package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserEditDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginPostDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.Claims;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final Environment env;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, Environment env) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.env = env;
    }

    /**
     * Gets the user with provided id
     * @param id id of user to be returned
     * @return user
     */
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

    public UserLoginPostDto loginOrRegisterUser(GoogleIdToken.Payload payload, String refreshToken){
        boolean isNewUser = false;
        UserLoginPostDto userLoginPostDto = new UserLoginPostDto();
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
            userLoginPostDto.setUserId(user.getId());
        }else{
            User user = optionalUser.get();

            user.setStatus(OnlineStatus.ONLINE);
            user.setToken(refreshToken);
            userRepository.saveAndFlush(user);
            userLoginPostDto.setUserId(user.getId());
        }

        userLoginPostDto.setIsNewUser(isNewUser);
        return userLoginPostDto;
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

    /**
     * Updates the location of the provided user
     * @param userId the id of the user to be updated
     * @param newLocation the new location of the user
     */
    @Transactional
    public void updateUserLocation(Long userId, Point newLocation) {
        this.userRepository.updateUserLocation(userId, newLocation);
    }

    public String validateAndGetUserEmailFromRefreshToken(String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken, SecurityConstants.REFRESH_SECRET)) {
            Claims claims = jwtTokenUtil.getClaimsFromJWT(refreshToken, SecurityConstants.REFRESH_SECRET);
            String emailId = claims.getSubject();
            Optional<User> optionalUser = userRepository.findByEmail(emailId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                if (user.getToken().equals(refreshToken)) {
                    return emailId;
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }

    @Transactional
    public UserLoginPostDto updateRefreshTokenForUser(String newRefreshToken, String emailId) {
        UserLoginPostDto userLoginPostDto = new UserLoginPostDto();
        Optional<User> optionalUser = userRepository.findByEmail(emailId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setToken(newRefreshToken);
            userRepository.saveAndFlush(user);
            userLoginPostDto.setUserId(user.getId());
        }
        return userLoginPostDto;
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

    /**
     * Returns a list of Users in the provided area/polygon
     * @param areaFilterPolygon the area from where users are returned
     * @return list of Users
     */
    public List<User> getAllUsersInArea(Polygon areaFilterPolygon){
        return this.userRepository.findByArea(areaFilterPolygon);
    }

    /**
     * Returns a list of all Users but the authenticated one
     * @return list of Users without the authenticated user
     */
    public List<User> getAllUsers(){
        UserOverviewDto currentUser = (UserOverviewDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findAll(currentUser.getId());
    }

    @Transactional
    public User getUserDetails(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        }
    }

    @Transactional
    public void updateUserDetails(Long userId, UserEditDto userEditDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBio(userEditDto.getBio());
            userRepository.saveAndFlush(user);
        }
    }
}
