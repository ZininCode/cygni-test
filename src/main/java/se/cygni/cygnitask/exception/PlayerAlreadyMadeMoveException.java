package se.cygni.cygnitask.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class PlayerAlreadyMadeMoveException extends GameException {
    private final String playerName;

    public PlayerAlreadyMadeMoveException(UUID gameId, String playerName, String descriptionMessage) {
      super(gameId, descriptionMessage);
        this.playerName = playerName;
    }
}
