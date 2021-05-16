package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Tag;
import ch.uzh.ifi.hase.soprafs21.rest.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagDTOMapper {
    TagDTOMapper INSTANCE = Mappers.getMapper(TagDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tagType", target = "tagType")
    TagDto toTagDto(Tag entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tagType", target = "tagType")
    Tag toTagEntity(TagDto tagDto);
}
