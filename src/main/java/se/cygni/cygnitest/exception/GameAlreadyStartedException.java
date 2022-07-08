package se.cygni.cygnitest.exception;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
public class GameAlreadyStartedException extends GameException {
    public GameAlreadyStartedException(UUID gameId) {
        super(gameId);
    }
}
