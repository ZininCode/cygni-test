package se.cygni.cygnitest.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.cygni.cygnitest.dto.GameDto;
import se.cygni.cygnitest.exception.*;
import se.cygni.cygnitest.helper.GameResultHelper;
import se.cygni.cygnitest.mapper.GameMapper;
import se.cygni.cygnitest.model.Game;
import se.cygni.cygnitest.repository.GameRepository;
import se.cygni.cygnitest.rest.api.GameStatus;
import se.cygni.cygnitest.rest.api.MoveEnum;
import se.cygni.cygnitest.service.GameService;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Service
@AllArgsConstructor
@Slf4j

public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final GameResultHelper gameResultHelper;
    private final GameMapper gameMapper;

    @Override
    public UUID createGame(String playerName) {
        Game game = Game.builder()
                .id(UUID.randomUUID())
                .player1(playerName)
                .status(GameStatus.CREATED)
                .build();
        repository.addGame(game);
        log.debug("Player {} created game {}", playerName, game.getId());

        return game.getId();
    }

    private Game findGame(UUID gameId) throws GameNotFoundException {
        return repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @Override
    public void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameException, GameAlreadyStartedException {
        Game game = findGame(gameId);
        if (!game.getStatus().equals(GameStatus.CREATED)) {
            log.debug("Game {} already in stared", gameId);
            throw new GameAlreadyStartedException(gameId);
        }
        if (game.getPlayer1().equals(playerName)) {
            log.debug("Player {} already in game {}", playerName, gameId);
            throw new JoinGameException(gameId, playerName);
        }
        repository.addPlayerToGame(gameId, playerName);
        log.debug("Player {} joined game {}", playerName, gameId);
    }

    @Override
    public void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMakeMoveException {
        Game game = findGame(gameId);
        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} not in progress", gameId);
            throw new GameNotInProgressException(gameId);
        }

        try {
            if (game.getPlayer1().equals(playerName)) {
                repository.movePlayer1(gameId, move);
            } else if (game.getPlayer2().equals(playerName)) {
                repository.movePlayer2(gameId, move);
            } else {
                throw new GameNotFoundException(gameId);
            }
        } catch (IllegalStateException e) {
            throw new PlayerAlreadyMakeMoveException(gameId, playerName);
        }

        game = findGame(gameId);
        if (game.getPlayer1Move() != null && game.getPlayer2Move() != null) {
            finishGame(game);
        }
    }

    @Override
    public GameDto getGameResult(UUID gameId) throws GameNotFoundException {
        Game game = findGame(gameId);

        return gameMapper.toGameDto(game);
    }

    private void finishGame(Game game) {
        log.debug("Both player made moves for game {}", game.getId());
        if (game.getPlayer1Move().equals(game.getPlayer2Move())) {
            repository.makeDraw(game.getId());
            log.debug("Game {} finished with draw", game.getId());
        } else {
            boolean firstPlayerWins = gameResultHelper.test(game.getPlayer1Move(), game.getPlayer2Move());
            if (firstPlayerWins) {
                repository.makePlayer1Wins(game.getId());
                log.debug("Game {} finished, player {} wins", game.getId(), game.getPlayer1());
            } else {
                repository.makePlayer2Wins(game.getId());
                log.debug("Game {} finished, player {} wins", game.getId(), game.getPlayer2());
            }
        }
    }
}
