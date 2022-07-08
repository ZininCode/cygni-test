package se.cygni.cygnitest.helper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import se.cygni.cygnitest.rest.api.MoveEnum;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MovePair {
    private MoveEnum move1;
    private MoveEnum move2;
}
