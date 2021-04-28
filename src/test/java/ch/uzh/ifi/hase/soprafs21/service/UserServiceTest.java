package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserEditDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.webtoken.JsonWebSignature;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testUpdateUserDetails(){
        Long mockUserId = 1L;
        UserEditDto mockedEditDto = new UserEditDto();
        mockedEditDto.setBio("Updated Bio");

        User mockedUser = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").build();
        User mockedUpdatedUser = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").bio("Updated Bio").build();

        when(userRepository.findById(mockUserId)).thenReturn(Optional.of(mockedUser));
        when(userRepository.saveAndFlush(mockedUser)).thenAnswer(i -> mockedUpdatedUser);

        userService.updateUserDetails(mockUserId,mockedEditDto);

        assertEquals(mockedEditDto.getBio(),mockedUpdatedUser.getBio());
    }

    @Test
    void testUpdateUserDetailsFailure(){
        Long mockUserId = 1L;
        UserEditDto mockedEditDto = new UserEditDto();
        mockedEditDto.setBio("Updated Bio");

        User mockedUser = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").build();

        given(userRepository.findById(mockUserId)).willReturn(Optional.empty());
        userService.updateUserDetails(mockUserId,mockedEditDto);

        verify(userRepository,times(0)).saveAndFlush(mockedUser);
    }

    @Test
    void verifyEmailIdForToken(){
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail("test@google.com");
        GoogleIdToken token = new GoogleIdToken(new JsonWebSignature.Header(),payload,new byte[10], new byte[10]);
        String email="test@google.com";
        assertEquals(Boolean.TRUE,userService.verifyEmailIdForToken(token,email));
    }

    @Test
    void verifyEmailIdForTokenFailure(){
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail("test@google.com");
        GoogleIdToken token = new GoogleIdToken(new JsonWebSignature.Header(),payload,new byte[10], new byte[10]);
        String email="test2@google.com";
        assertThrows(ResponseStatusException.class, () -> userService.verifyEmailIdForToken(token,email));
    }

    @Test
    void verifyloginOrRegisterForFirstTimeUsers(){
        String email="test@google.com";
        String refreshToken ="refreshToken";
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail(email);
        payload.setSubject("google.com");
        payload.set("name", "test");
        payload.set("picture", "url1");
        payload.setIssuer("google");

        User mockedUser = User.builder().id(1l).providerUid(payload.getSubject())
                .provider(payload.getIssuer())
                .name((String) payload.get("name"))
                .profilePictureURL((String) payload.get("picture"))
                .email(payload.getEmail())
                .status(OnlineStatus.ONLINE)
                .token(refreshToken).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.saveAndFlush(mockedUser)).thenAnswer(i -> mockedUser);

        assertEquals(Boolean.TRUE,userService.loginOrRegisterUser(payload,refreshToken));
    }


}
