package se.cygni.cygnitest.service;

import se.cygni.cygnitest.dto.GameDto;
import se.cygni.cygnitest.exception.*;
import se.cygni.cygnitest.rest.api.MoveEnum;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
public interface GameService {
    UUID createGame(String playerName);

    void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameException, GameAlreadyStartedException;

    void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMakeMoveException;

    GameDto getGameResult(UUID gameId) throws GameNotFoundException;
}
