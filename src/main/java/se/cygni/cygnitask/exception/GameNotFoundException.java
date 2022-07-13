package se.cygni.cygnitask.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class GameNotFoundException extends GameException {
    public GameNotFoundException(UUID gameId, String descriptionMessage) {
        super(gameId, descriptionMessage);
    }
}

