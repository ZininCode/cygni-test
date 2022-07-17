package se.cygni.cygnitask.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.PlayerAlreadyMadeMoveException;
import se.cygni.cygnitask.helper.GameResolver;
import se.cygni.cygnitask.helper.GameResultHelper;
import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.mapper.GameMapperImpl;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.response.GameStatus;
import se.cygni.cygnitask.rest.api.response.MoveEnum;
import se.cygni.cygnitask.service.GamePlayService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamePlayServiceImplTest {

    @Mock
    private GameRepository repository;
    private GamePlayService gamePlayService;

    @BeforeEach
    public void initService() {
        GameResultHelper gameResultHelper = new GameResultHelper();
        GameMapper gameMapper = new GameMapperImpl();
        repository = new GameRepository();
        GameResolver gameResolver = new GameResolver(repository, gameResultHelper);
        gamePlayService = new GamePlayServiceImpl(repository, gameResolver, gameMapper);
    }

    @Test
    void player1MakesFirstMoveTest() throws GameNotFoundException, PlayerAlreadyMadeMoveException, GameNotInProgressException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        repository.addGame(game);
        gamePlayService.makeMove(gameId, "Bob", MoveEnum.ROCK);
        assertEquals(game.getPlayer1Move(), MoveEnum.ROCK);
    }

    @Test
    void player2MakesFirstMoveGameParametersTest() throws GameNotFoundException, PlayerAlreadyMadeMoveException, GameNotInProgressException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        repository.addGame(game);

        gamePlayService.makeMove(gameId, "Cat", MoveEnum.ROCK);
        assertEquals(game.getPlayer2Move(), MoveEnum.ROCK);
    }

    @Test
    void player2MakesSecondMoveSolveGameTest()
            throws GameNotFoundException, PlayerAlreadyMadeMoveException, GameNotInProgressException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        repository.addGame(game);
        gamePlayService.makeMove(gameId, "Cat", MoveEnum.PAPER);
        assertTrue(game.getPlayer2Move().equals(MoveEnum.PAPER) && game.getStatus().equals(GameStatus.HAS_WINNER));
    }

    @Test
    void getGameResultWhenInProgress() throws GameNotFoundException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        repository.addGame(game);
        GameDto response = gamePlayService.getGameResult(gameId);
        assertEquals(response.getStatus(), GameStatus.IN_PROGRESS);
    }

    @Test
    void getGameResultWhenInHasWinner() throws GameNotFoundException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.PAPER)
                .status(GameStatus.HAS_WINNER)
                .winner("Cat")
                .build();

        repository.addGame(game);
        GameDto response = gamePlayService.getGameResult(gameId);
        assertEquals(response.getStatus(), GameStatus.HAS_WINNER);
    }
}
