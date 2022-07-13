package se.cygni.cygnitask.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class JoinGameSamePlayerNameException extends GameException {
    private final String playerName;
    public JoinGameSamePlayerNameException(UUID gameId, String playerName, String descriptionMessage) {
        super(gameId, descriptionMessage);
        this.playerName = playerName;
    }
}
