package se.cygni.cygnitask.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameResultHelperTest {

    @Mock
    GameResultHelper gameResultHelper;

    @BeforeEach
    public void initService() {
        gameResultHelper = new GameResultHelper();
    }

    @Test
    void matchResultFirstMoveWinsTest() {
        boolean rockPaper = gameResultHelper.matchResult(MoveEnum.ROCK, MoveEnum.PAPER);
        boolean rockScissors = gameResultHelper.matchResult(MoveEnum.ROCK, MoveEnum.SCISSORS);
        boolean paperRock = gameResultHelper.matchResult(MoveEnum.PAPER, MoveEnum.ROCK);
        boolean paperScissors = gameResultHelper.matchResult(MoveEnum.PAPER, MoveEnum.SCISSORS);
        boolean scissorsRock = gameResultHelper.matchResult(MoveEnum.SCISSORS, MoveEnum.ROCK);
        boolean scissorsPaper = gameResultHelper.matchResult(MoveEnum.SCISSORS, MoveEnum.PAPER);
        assertTrue(rockScissors);
        assertTrue(paperRock);
        assertTrue(scissorsPaper);
        assertFalse(rockPaper);
        assertFalse(paperScissors);
        assertFalse(scissorsRock);
    }
}
