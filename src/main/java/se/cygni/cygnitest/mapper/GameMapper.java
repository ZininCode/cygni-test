package se.cygni.cygnitest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.cygni.cygnitest.model.Game;
import se.cygni.cygnitest.dto.GameResultResponse;

/**
 * Date: 08.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Mapper
public interface GameMapper {
    @Mapping(source = "id", target = "gameId")
    GameResultResponse toGameResultResponse(Game game);
}
