package se.cygni.cygnitask.rest.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * A response with the game id.
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
@Builder

public class GameCreateResponse {
    private UUID gameId;
}
