package se.cygni.cygnitest.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class PlayerAlreadyMadeMoveException extends GameException {
    private String playerName;
    private String descriptionMessage;

  public PlayerAlreadyMadeMoveException(UUID gameId, String playerName, String descriptionMessage) {
        super(gameId);
        this.playerName = playerName;
        this.descriptionMessage = descriptionMessage;
    }
}
