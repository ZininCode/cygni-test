package se.cygni.cygnitest.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
public class GameNotFoundException extends GameException {
    public GameNotFoundException(UUID gameId) {
        super(gameId);
    }
}
