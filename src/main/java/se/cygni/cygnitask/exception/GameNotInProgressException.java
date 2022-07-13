package se.cygni.cygnitask.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class GameNotInProgressException extends GameException {
    public GameNotInProgressException(UUID gameId, String descriptionMessage) {
        super(gameId, descriptionMessage);
    }
}
