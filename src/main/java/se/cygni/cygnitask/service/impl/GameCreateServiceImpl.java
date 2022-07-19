package se.cygni.cygnitask.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.cygni.cygnitask.exception.*;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.service.GameCreateService;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
@Service
@AllArgsConstructor
@Slf4j

public class GameCreateServiceImpl implements GameCreateService {
    private final GameRepository repository;

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



    @Override
    public void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, GameNotInProgressException, JoinFullGameException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        if (game.getPlayer1().equals(playerName)) {
            log.debug("Player {} already in game {}", playerName, gameId);
            throw new JoinGameSamePlayerNameException(gameId, "The game has already the player with this name" +playerName);
        }

        if (game.getPlayer2() != null) {
            log.debug("2 players are already in game {}", gameId);
            throw new JoinFullGameException(gameId, "The game have already all players set");
        }

        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} already finished", gameId);
            throw new GameNotInProgressException (gameId, "The game have already finished");
        }

        repository.addPlayerToGame(gameId, playerName);
        log.debug("Player {} joined game {}", playerName, gameId);
    }
}
