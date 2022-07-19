package se.cygni.cygnitask.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.helper.GameResolver;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.PlayerAlreadyMadeMoveException;
import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.service.GamePlayService;

import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
@Service
@AllArgsConstructor
@Slf4j

public class GamePlayServiceImpl implements GamePlayService {
    private final GameRepository repository;
    private final GameResolver gameResolver;
    private final GameMapper gameMapper;

    @Override
    public void makeMove(UUID gameId, String playerName, MoveEnum move)
            throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} not in progress", gameId);
            throw new GameNotInProgressException(gameId, "The game is not in progress");
        }

        try {
            if (game.getPlayer1().equals(playerName)) {
                repository.player1Move(gameId, move);
            } else if (game.getPlayer2().equals(playerName)) {
                repository.player2Move(gameId, move);
            } else {
                throw new GameNotFoundException(gameId, "Game not found");
            }
        } catch (IllegalStateException e) {
            throw new PlayerAlreadyMadeMoveException(gameId, "The player "+ playerName+ "  have already made a move");
        }

        if (game.getPlayer1Move() != null && game.getPlayer2Move() != null) {
            gameResolver.resolveGame(game);
        }
    }

    @Override
    public GameDto getGameResult(UUID gameId) throws GameNotFoundException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        return gameMapper.toGameDto(game);
    }
}
