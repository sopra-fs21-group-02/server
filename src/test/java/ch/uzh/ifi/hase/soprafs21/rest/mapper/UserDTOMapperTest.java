package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
class UserDTOMapperTest {
    @Test
    void toOverviewDTO() {
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(8.461859195948641, 47.35997146785179));
        User user = User.builder().id(1L).email("email1").name("name1").profilePictureURL("url1").status(OnlineStatus.ONLINE).lastUserLocation(newLocation).build();

        UserOverviewDto overviewDto = UserDTOMapper.INSTANCE.toOverviewDTO(user);
        assertEquals(user.getId(), overviewDto.getId());
        assertEquals(user.getEmail(), overviewDto.getEmail());
        assertEquals(user.getName(), overviewDto.getName());
        assertEquals(user.getProfilePictureURL(), overviewDto.getProfilePicture());
        assertEquals(OnlineStatusDto.fromValue(user.getStatus().getValue()), overviewDto.getStatus());
        assertEquals(newLocation.getX(), overviewDto.getLatestLocation().getLongitude());
        assertEquals(newLocation.getY(), overviewDto.getLatestLocation().getLatitude());
    }
}
