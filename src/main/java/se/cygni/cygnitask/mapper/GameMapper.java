package se.cygni.cygnitask.mapper;

import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.rest.api.response.GameResultResponse;

/**
 * Date: 08.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Mapper
public interface GameMapper {
    GameDto toGameDto(Game game);

    @Mapping(source = "id", target = "gameId")
    GameResultResponse toGameResultResponse(GameDto gameDto);
}
