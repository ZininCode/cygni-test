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
import se.cygni.cygnitest.dto.MoveEnum;
import se.cygni.cygnitest.service.GamePlayService;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Service
@AllArgsConstructor
@Slf4j

public class GamePlayServiceImpl implements GamePlayService {
    private final GameRepository repository;
    private final GameResultHelper gameResultHelper;
    private final GameMapper gameMapper;


    @Override
    public void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));
        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} not in progress", gameId);
            throw new GameNotInProgressException(gameId, "The game is not in progress");
        }

        try {
            if (game.getPlayer1().equals(playerName)) {
                repository.movePlayer1(gameId, move);
            } else if (game.getPlayer2().equals(playerName)) {
                repository.movePlayer2(gameId, move);
            } else {
                throw new GameNotFoundException(gameId, "Game not found");
            }
        } catch (IllegalStateException e) {
            throw new PlayerAlreadyMadeMoveException(gameId, playerName, "The player have already made a move");
        }

        if (game.getPlayer1Move() != null && game.getPlayer2Move() != null) {
            finishGame(game);
        }
    }

    @Override
    public Game getGameResult(UUID gameId) throws GameNotFoundException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        return game;
    }

    private void finishGame(Game game) {
        log.debug("Both player made moves for game {}", game.getId());
        if (game.getPlayer1Move().equals(game.getPlayer2Move())) {
            repository.makeDraw(game.getId());
            log.debug("Game {} finished with draw", game.getId());
        } else {
            boolean firstPlayerWins = gameResultHelper.matchResult(game.getPlayer1Move(), game.getPlayer2Move());
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
