package se.cygni.cygnitest.service;

import se.cygni.cygnitest.exception.*;
import se.cygni.cygnitest.model.Game;
import se.cygni.cygnitest.dto.MoveEnum;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
public interface GamePlayService {
    void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException;

    Game getGameResult(UUID gameId) throws GameNotFoundException;
}
