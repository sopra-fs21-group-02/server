package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.TagType;
import ch.uzh.ifi.hase.soprafs21.entity.Tag;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagDTOMapperTest {
    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void initialization() throws IOException {
        User owner = User.builder().id(1L).email("henry@dogs.de").status(OnlineStatus.ONLINE).name("Henry").profilePictureURL("Some picture").build();
        tag = Tag.builder().id(1L).name("Chat").tagType(TagType.OFFERING).owner(owner).build();

        tagDto = new TagDto();
        tagDto.setId(2L);
        tagDto.setName("Walking Buddies");
        tagDto.setTagType(TagDto.TagTypeEnum.OFFERING);
    }

    @Test
    void toTagDTO() {
        TagDto tagDto = TagDTOMapper.INSTANCE.toTagDto(tag);

        assertEquals(tag.getId(), tagDto.getId());
        assertEquals(tag.getName(), tagDto.getName());
        assertEquals(tag.getTagType().toString(), tagDto.getTagType().toString());
    }

    @Test
    void toTagEntity() throws IOException {
        Tag newTag = TagDTOMapper.INSTANCE.toTagEntity(tagDto);

        assertEquals(tagDto.getId(), newTag.getId());
        assertEquals(tagDto.getName(), newTag.getName());
        assertEquals(tagDto.getTagType().toString(), newTag.getTagType().toString());
    }
}