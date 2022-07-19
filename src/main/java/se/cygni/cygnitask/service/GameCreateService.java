package se.cygni.cygnitask.service;
import se.cygni.cygnitask.exception.*;

import java.util.UUID;

/**
 * Create a game and two players join the game.
 *
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
public interface GameCreateService {

    /**
     * Create a new game, assign initial parameters in the game, save the game in game repository and return game's id.
     *
     * @param playerName: Name of the player who creates the game.
     * @return id of the created game.
     */
    UUID createGame(String playerName);

    /**
     * Another player joins the game.
     *
     * @param gameId id of the game used to find the game in the game repository.
     * @param playerName Name of the second player in the game.
     * @throws GameNotFoundException If there is no game with given id found in the game repository.
     * @throws JoinGameSamePlayerNameException If the game has already the player with this name.
     * @throws GameNotInProgressException If the game have already finished.
     * @throws JoinFullGameException If the game have already two players set.
     */
    void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, JoinFullGameException, GameNotInProgressException;

}
