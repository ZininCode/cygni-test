package se.cygni.cygnitask.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.JoinFullGameException;
import se.cygni.cygnitask.exception.JoinGameSamePlayerNameException;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.service.GameCreateService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 15.07.2022
 *
 * @author Nikolay Zinin
 */
class GameCreateServiceImplTest {

    private GameCreateService gameCreateService;
    private GameRepository repository;

    @BeforeEach
    public void initService() {
        repository = new GameRepository();
        gameCreateService = new GameCreateServiceImpl(repository);
    }

    @Test
    void createGameAndControlPlayerNameTest() throws GameNotFoundException {
        UUID gameId = gameCreateService.createGame("Bob");
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));
        assertEquals(game.getPlayer1(), "Bob");
        assertEquals(game.getStatus(), GameStatus.IN_PROGRESS);
    }

    @Test
    void joinGameAndControlPlayerNameTest() throws GameNotFoundException, JoinFullGameException, GameNotInProgressException, JoinGameSamePlayerNameException {
        UUID gameId = gameCreateService.createGame("Bob");
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));
        gameCreateService.joinGame(gameId, "Cat");
        String player1 = game.getPlayer2();
        assertEquals(player1, "Cat");
    }
}
