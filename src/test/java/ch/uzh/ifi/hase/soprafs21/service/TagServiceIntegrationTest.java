package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.TagType;
import ch.uzh.ifi.hase.soprafs21.entity.Tag;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.TagRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(value = {"/data_init.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    private Tag tag;

    @BeforeEach
    void setUp() throws IOException {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        tag = Tag.builder()
                .name("Walking buddies")
                .tagType(TagType.OFFERING)
                .owner(userRepository.getOne(1L)).build();
        tagRepository.saveAndFlush(tag);
    }

    @Test
    void testAddTag(){
        User user = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").build();
        Tag tagToAdd = Tag.builder().id(1l).name("Chat").owner(user).tagType(TagType.OFFERING).build();
        Tag actualTag = tagService.addTag(tagToAdd);

        assertEquals(tagToAdd.getName(), actualTag.getName());
        assertEquals(tagToAdd.getTagType(), actualTag.getTagType());
        assertEquals(tagToAdd.getOwner().getId(), actualTag.getOwner().getId());
    }

    @Test
    void testDeleteTag(){
        tagService.deleteTag(tag.getId(), tag.getOwner().getId());
        Optional<Tag> notExistingTag = tagRepository.findById(tag.getId());
        assertTrue(notExistingTag.isEmpty());
    }

    @Test
    void testDeleteNotExistingTag(){
        Exception ex = assertThrows(ResponseStatusException.class, () -> tagService.deleteTag(3L, 1L));

        String expectedMessage = "No Tag with provided id exists";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteTagUnauthorizedUser(){
        Exception ex = assertThrows(ResponseStatusException.class, () -> tagService.deleteTag(tag.getId(), 3L));

        String expectedMessage = "User is not permitted to delete another user tags";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAddTagUnauthorizedUser(){
        tag.setOwner(userRepository.getOne(2L));

        Exception ex = assertThrows(ResponseStatusException.class, () -> tagService.addTag(tag));

        String expectedMessage = "User is not permitted to manipulate this tag";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAddDoubleTag(){

        Tag doubleTag = Tag.builder().name("Walking buddies").tagType(TagType.OFFERING).owner(userRepository.getOne(1L)).build();

        Exception ex = assertThrows(ResponseStatusException.class, () -> tagService.addTag(doubleTag));

        String expectedMessage = "Tag is already added";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}