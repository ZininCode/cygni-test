package se.cygni.cygnitask.rest.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Date: 08.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@Setter
@Builder

public class GameResultResponse {
    private UUID gameId;
    private GameStatus status;
    private String winner;
}
