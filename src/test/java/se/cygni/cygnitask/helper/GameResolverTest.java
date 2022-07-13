package se.cygni.cygnitask.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.response.GameStatus;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameResolverTest {

    @InjectMocks
    private GameRepository repository;
    private GameResolver gameResolver;
    private GameResultHelper gameResultHelper;

    @Mock
    private AutoCloseable closeable;

    @BeforeEach
    public void initService() {
        gameResultHelper = new GameResultHelper();
        closeable = MockitoAnnotations.openMocks(this);
        gameResolver = new GameResolver(repository, gameResultHelper);
    }

    @AfterEach
    void tearDown()  throws Exception {
        closeable.close();
    }
    @Test
    void resolveGameWhenOnePlayerWins() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.PAPER)
                .status(GameStatus.IN_PROGRESS)
                .winner("Cat")
                .build();

        repository.addGame(game);
        gameResolver.resolveGame(game);
        assertTrue(game.getStatus().equals(GameStatus.HAS_WINNER));
    }

    @Test
    void resolveGameWhenResultIsDraw() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .winner("Cat")
                .build();

        repository.addGame(game);
        gameResolver.resolveGame(game);
        assertTrue(game.getStatus().equals(GameStatus.DRAW));
    }
}
