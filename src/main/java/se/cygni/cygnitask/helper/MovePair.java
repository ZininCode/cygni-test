package se.cygni.cygnitask.helper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode

public class MovePair {
    private MoveEnum move1;
    private MoveEnum move2;
}
