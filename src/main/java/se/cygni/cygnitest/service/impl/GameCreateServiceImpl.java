package se.cygni.cygnitest.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.cygni.cygnitest.exception.*;
import se.cygni.cygnitest.helper.GameResultHelper;
import se.cygni.cygnitest.mapper.GameMapper;
import se.cygni.cygnitest.model.Game;
import se.cygni.cygnitest.repository.GameRepository;
import se.cygni.cygnitest.dto.GameStatus;
import se.cygni.cygnitest.service.GameCreateService;

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
    private final GameResultHelper gameResultHelper;
    private final GameMapper gameMapper;

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
    public void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, GameAlreadyFinishedException, JoinFullGameException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        if (game.getPlayer1().equals(playerName)) {
            log.debug("Player {} already in game {}", playerName, gameId);
            throw new JoinGameSamePlayerNameException(gameId, playerName, "The game has already the player with this name");
        }

        if (game.getPlayer2() != null) {
            log.debug("2 players are already in game {}", playerName, gameId);
            throw new JoinFullGameException(gameId, "The game have already all players set");
        }

        if (game.getStatus().equals(GameStatus.FINISHED)) {
            log.debug("Game {} already finished", gameId);
            throw new GameAlreadyFinishedException(gameId, "The game have already finished");
        }

        repository.addPlayerToGame(gameId, playerName);
        log.debug("Player {} joined game {}", playerName, gameId);
    }
}
