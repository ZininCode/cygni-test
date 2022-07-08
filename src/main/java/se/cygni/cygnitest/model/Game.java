package se.cygni.cygnitest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.cygni.cygnitest.rest.api.GameStatus;
import se.cygni.cygnitest.rest.api.MoveEnum;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@Setter
@ToString
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
