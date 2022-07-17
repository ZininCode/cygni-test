package se.cygni.cygnitask.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.response.GameStatus;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameResolverTest {

    @Mock
    private GameRepository repository;
    private GameResolver gameResolver;

    @Mock
    private AutoCloseable closeable;

    @BeforeEach
    public void initService() {
        repository = new GameRepository();
        GameResultHelper gameResultHelper = new GameResultHelper();
        gameResolver = new GameResolver(repository, gameResultHelper);
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
        assertEquals(game.getStatus(), GameStatus.HAS_WINNER);
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
        assertEquals(game.getStatus(), GameStatus.DRAW);
    }
}
