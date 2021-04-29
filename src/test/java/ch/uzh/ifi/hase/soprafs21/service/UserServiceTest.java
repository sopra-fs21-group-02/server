package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserEditDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
