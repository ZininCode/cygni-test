package se.cygni.cygnitest.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class JoinGameException extends GameException {
    private final String playerName;

    public JoinGameException(UUID gameId, String playerName) {
        super(gameId);
        this.playerName = playerName;
    }
}
