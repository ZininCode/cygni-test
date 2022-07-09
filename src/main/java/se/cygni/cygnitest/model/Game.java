package se.cygni.cygnitest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitest.dto.GameStatus;
import se.cygni.cygnitest.dto.MoveEnum;

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
