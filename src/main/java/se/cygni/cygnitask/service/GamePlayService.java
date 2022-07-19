package se.cygni.cygnitask.service;

import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.PlayerAlreadyMadeMoveException;

import java.util.UUID;

/**
 * Play the game: both of the players make moves and the result returns in response
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
public interface GamePlayService {
    /**
     *Play the game: player1 and player2 making play moves independently. After both of them made the moves,
     * the game is resolving and the result is generated into the Game class.
     *
     * @param gameId id of the game used to find the game in the game repository.
     * @param playerName Name of the player in the game who making the move.
     * @param move Move of the player in the game: must be one of the Enum: ROCK, PAPER or SCISSORS.
     * @throws GameNotFoundException If there is no game with given id found in the game repository.
     * @throws GameNotInProgressException If the game with the given id is already finished: game status is has a winner or a draw.
     * @throws PlayerAlreadyMadeMoveException If player with this name have already made a move.
     *
     */
    void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException;

    GameDto getGameResult(UUID gameId) throws GameNotFoundException;
}
