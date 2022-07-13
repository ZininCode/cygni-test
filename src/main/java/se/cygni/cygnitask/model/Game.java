package se.cygni.cygnitask.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitask.rest.api.response.GameStatus;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@Setter
@Builder
public class Game {
    private UUID id;
    private String player1;
    private String player2;
    private MoveEnum player1Move;
    private MoveEnum player2Move;
    private GameStatus status;
    private String winner;
}
