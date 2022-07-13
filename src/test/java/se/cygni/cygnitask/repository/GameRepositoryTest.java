package se.cygni.cygnitask.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.helper.GameResultHelper;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.rest.api.response.GameStatus;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameRepositoryTest {

    @InjectMocks
    private GameRepository repository;
    private GameResultHelper gameResultHelper;

    @Mock
    private AutoCloseable closeable;

    @BeforeEach
    public void initService() {
        gameResultHelper = new GameResultHelper();
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void addGameAndFindGameTest() throws GameNotFoundException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .build();
        repository.addGame(game);
        Game gameFromRepository = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));
        assertEquals(gameFromRepository, game);
    }

    @Test
    void addPlayerToGame() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .build();
        repository.addGame(game);
        String player = "Bob";
        repository.addPlayerToGame(gameId, player);
        assertEquals(game.getPlayer2(), "Bob");
    }

    @Test
    void movePlayer1Test() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        repository.addGame(game);
        repository.player1Move(gameId, MoveEnum.ROCK);
        assertEquals(game.getPlayer1Move(), MoveEnum.ROCK);
    }

    @Test
    void movePlayer2Test() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        repository.addGame(game);
        repository.player2Move(gameId, MoveEnum.ROCK);
        assertEquals(game.getPlayer2Move(), MoveEnum.ROCK);
    }


    @Test
    void makeDrawTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        repository.addGame(game);
        repository.makeDraw(gameId);
        assertEquals(game.getStatus(), GameStatus.DRAW);
    }

    @Test
    void makePlayer1WinsTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.PAPER)
                .player2Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        repository.addGame(game);
        repository.makePlayer1Wins(gameId);
        assertTrue(game.getStatus().equals(GameStatus.HAS_WINNER) && game.getWinner().equals("Bob"));
    }

    @Test
    void makePlayer2WinsTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.PAPER)
                .status(GameStatus.IN_PROGRESS)
                .build();

        repository.addGame(game);
        repository.makePlayer2Wins(gameId);
        assertTrue(game.getStatus().equals(GameStatus.HAS_WINNER) && game.getWinner().equals("Cat"));
    }
}
