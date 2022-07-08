package se.cygni.cygnitest.helper;

import org.springframework.stereotype.Component;
import se.cygni.cygnitest.rest.api.MoveEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Component
public class GameResultHelper {
    private static final Map<MovePair, Boolean> PAIR_RESULT_MAP = new HashMap<>();

    static {
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.ROCK, MoveEnum.PAPER), false);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.ROCK, MoveEnum.SCISSORS), true);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.PAPER, MoveEnum.ROCK), true);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.PAPER, MoveEnum.SCISSORS), false);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.SCISSORS, MoveEnum.ROCK), false);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.SCISSORS, MoveEnum.PAPER), true);
    }

    public boolean test(MoveEnum player1Move, MoveEnum player2Move) {
        return PAIR_RESULT_MAP.get(new MovePair(player1Move, player2Move));
    }
}
