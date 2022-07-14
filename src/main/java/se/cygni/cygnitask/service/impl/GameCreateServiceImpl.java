package se.cygni.cygnitask.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.cygni.cygnitask.rest.api.response.GameStatus;
import se.cygni.cygnitask.exception.GameAlreadyFinishedException;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.JoinFullGameException;
import se.cygni.cygnitask.exception.JoinGameSamePlayerNameException;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.service.GameCreateService;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Service
@AllArgsConstructor
@Slf4j

public class GameCreateServiceImpl implements GameCreateService {
    private final GameRepository repository;

    /**
     * Creates a new game, assigns initial parameters in the game, saves the game in game repository and returns game's id.
     *
     * @param playerName: Name of the player who creates the game.
     * @return id of the created game.
     */
    @Override
    public UUID createGame(String playerName) {
        Game game = Game.builder()
                .id(UUID.randomUUID())
                .player1(playerName)
                .status(GameStatus.IN_PROGRESS)
                .build();
        repository.addGame(game);
        log.debug("Player {} created game {}", playerName, game.getId());

        return game.getId();
    }

    /**
     * Another player joins the game.
     *
     * @param gameId id of the game used to find the game in the game repository.
     * @param playerName Name of the second player in the game.
     * @throws GameNotFoundException If there is no game with given id found in the game repository.
     * @throws JoinGameSamePlayerNameException If the game has already the player with this name.
     * @throws GameAlreadyFinishedException If the game have already finished.
     * @throws JoinFullGameException If the game have already two players set.
     */

    @Override
    public void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, GameAlreadyFinishedException, JoinFullGameException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        if (game.getPlayer1().equals(playerName)) {
            log.debug("Player {} already in game {}", playerName, gameId);
            throw new JoinGameSamePlayerNameException(gameId, playerName, "The game has already the player with this name");
        }

        if (game.getPlayer2() != null) {
            log.debug("2 players are already in game {}", gameId);
            throw new JoinFullGameException(gameId, "The game have already all players set");
        }

        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} already finished", gameId);
            throw new GameAlreadyFinishedException(gameId, "The game have already finished");
        }

        repository.addPlayerToGame(gameId, playerName);
        log.debug("Player {} joined game {}", playerName, gameId);
    }
}
