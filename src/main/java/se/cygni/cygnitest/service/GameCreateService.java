package se.cygni.cygnitest.service;
import se.cygni.cygnitest.exception.*;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
public interface GameCreateService {
    UUID createGame(String playerName);

    void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, GameAlreadyFinishedException, JoinFullGameException;

}
