package se.cygni.cygnitask.service;

import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.rest.api.response.MoveEnum;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.PlayerAlreadyMadeMoveException;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
public interface GamePlayService {
    void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException;

    GameDto getGameResult(UUID gameId) throws GameNotFoundException;
}
