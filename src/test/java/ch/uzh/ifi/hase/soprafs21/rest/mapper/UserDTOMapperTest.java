package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class UserDTOMapperTest {
    @Test
    public void toOverviewDTO() {
        User user = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").status(OnlineStatus.ONLINE).build();

        UserOverviewDto overviewDto = UserDTOMapper.INSTANCE.toOverviewDTO(user);
        assertEquals(user.getId(), overviewDto.getId());
        assertEquals(user.getEmail(), overviewDto.getEmail());
        assertEquals(user.getName(), overviewDto.getName());
        assertEquals(user.getProfilePictureURL(), overviewDto.getProfilePicture());
        assertEquals(OnlineStatusDto.fromValue(user.getStatus().getValue()), overviewDto.getStatus());
    }
}
