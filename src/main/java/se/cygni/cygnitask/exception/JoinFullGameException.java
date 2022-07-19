package se.cygni.cygnitask.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 10.07.2022
 *
 * @author Nikolay Zinin
 */
@Getter
public class JoinFullGameException extends GameException {
    public JoinFullGameException(UUID gameId, String descriptionMessage) {
        super(gameId, descriptionMessage);
    }
}
