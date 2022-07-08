package se.cygni.cygnitest.exception;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
public class GameNotInProgressException extends GameException {
    public GameNotInProgressException(UUID gameId) {
        super(gameId);
    }
}
