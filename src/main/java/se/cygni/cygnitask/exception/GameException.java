package se.cygni.cygnitask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@AllArgsConstructor
public class GameException extends Exception {
    private final UUID gameId;
    private final String descriptionMessage;
}
