package se.cygni.cygnitest.helper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.cygni.cygnitest.dto.MoveEnum;

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
