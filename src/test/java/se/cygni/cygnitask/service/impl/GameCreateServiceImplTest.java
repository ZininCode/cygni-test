package se.cygni.cygnitask.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.cygni.cygnitask.exception.GameAlreadyFinishedException;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.JoinFullGameException;
import se.cygni.cygnitask.exception.JoinGameSamePlayerNameException;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.service.GameCreateService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameCreateServiceImplTest {
    @InjectMocks
    private GameRepository repository;

    @Mock
    private GameCreateService gameCreateService;
    private AutoCloseable closeable;

    @BeforeEach
    public void initService() {
        closeable = MockitoAnnotations.openMocks(this);
        gameCreateService = new GameCreateServiceImpl(repository);

    }
    @AfterEach
    void tearDown()  throws Exception {
        closeable.close();
    }

    @Test
    void createGameAndControlPlayerNameTest() throws GameNotFoundException {
        UUID gameId = gameCreateService.createGame("Bob");
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));
        String player1 = game.getPlayer1();
        assertEquals(player1, "Bob");
    }

    @Test
    void joinGameAndControlPlayerNameTest() throws GameNotFoundException, JoinFullGameException, GameAlreadyFinishedException, JoinGameSamePlayerNameException {
        UUID gameId = gameCreateService.createGame("Bob");
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));
        gameCreateService.joinGame(gameId, "Cat");
        String player1 = game.getPlayer2();
        assertEquals(player1, "Cat");
    }




}
