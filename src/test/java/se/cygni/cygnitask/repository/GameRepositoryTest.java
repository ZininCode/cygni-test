package se.cygni.cygnitask.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Date: 15.07.2022
 *
 * @author Nikolay Zinin
 */
class GameRepositoryTest {

    @Mock
    private GameRepository repository;

    @BeforeEach
    public void initService() {
        repository = new GameRepository();
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
