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
public class JoinFullGameException extends GameException {
    private String descriptionMessage;

    public JoinFullGameException(UUID gameId, String descriptionMessage) {
        super(gameId);
        this.descriptionMessage = descriptionMessage;
    }
}
