package se.cygni.cygnitask.rest.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.exception.*;
import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.rest.api.request.CreateGameRequest;
import se.cygni.cygnitask.rest.api.request.JoinGameRequest;
import se.cygni.cygnitask.rest.api.request.MoveRequest;
import se.cygni.cygnitask.rest.api.response.GameCreateResponse;
import se.cygni.cygnitask.rest.api.response.GameResultResponse;
import se.cygni.cygnitask.service.GameCreateService;
import se.cygni.cygnitask.service.GamePlayService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Game controller.
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
@RestController
@RequestMapping(path = "/api/games", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j

public class GameController {
    private final GameCreateService gameCreateService;
    private final GamePlayService gamePlayService;
    private final GameMapper gameMapper;

    /**
     * Create a new game and set game status "in progress".
     * @param request name of the player who creates the game
     * @return Game id
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameCreateResponse create(@RequestBody @Valid CreateGameRequest request) {
        UUID gameId = gameCreateService.createGame(request.getName());

        return GameCreateResponse.builder().gameId(gameId).build();
    }

    /**
     *Second player joins the game.
     * @param gameId The game id.
     * @param request Name of the second player.
     * @throws JoinGameSamePlayerNameException If a player with this name have already joined the game.
     * @throws GameNotFoundException If the game is not found for this id.
     * @throws GameNotInProgressException If the game status is "not in progress".
     * @throws JoinFullGameException If the game have already all players set.
     */
    @PostMapping(path = "/{gameId}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid JoinGameRequest request) throws JoinGameSamePlayerNameException, GameNotFoundException, JoinFullGameException, GameNotInProgressException {
        gameCreateService.joinGame(gameId, request.getName());
    }

    /**
     * A player makes a move in the game.
     * @param gameId The game id.
     * @param request The move: allowed moves are: ROCK, PAPER or SCISSORS.
     * @throws GameNotInProgressException If the game status is "not in progress".
     * @throws GameNotFoundException If the game is not found for this id.
     * @throws PlayerAlreadyMadeMoveException If the player have already made this move
     */
    @PostMapping(path = "/{gameId}/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid MoveRequest request) throws GameNotInProgressException, GameNotFoundException, PlayerAlreadyMadeMoveException {
        gamePlayService.makeMove(gameId, request.getName(), request.getMove());
    }

    /**
     * Return game result.
     * @param gameId The game id.
     * @return Game result: "has a winner" and winner's name or a "draw": if game is finished. Or "game in progress" if game is not finished.
     * @throws GameNotFoundException If the game is not found for this id.
     */
    @GetMapping(path = "/{gameId}")
    public GameResultResponse read(@PathVariable("gameId") UUID gameId) throws GameNotFoundException {
        GameDto gameDto = gamePlayService.getGameResult(gameId);

        return gameMapper.toGameResultResponse(gameDto);
    }
}
