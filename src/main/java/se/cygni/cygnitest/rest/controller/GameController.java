package se.cygni.cygnitest.rest.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.cygni.cygnitest.model.Game;
import se.cygni.cygnitest.exception.*;
import se.cygni.cygnitest.mapper.GameMapper;
import se.cygni.cygnitest.rest.api.request.CreateGameRequest;
import se.cygni.cygnitest.rest.api.request.JoinGameRequest;
import se.cygni.cygnitest.rest.api.request.MoveRequest;
import se.cygni.cygnitest.dto.GameCreateResponse;
import se.cygni.cygnitest.dto.GameResultResponse;
import se.cygni.cygnitest.service.GameCreateService;
import se.cygni.cygnitest.service.GamePlayService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@RestController
@RequestMapping(path = "/api/games", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j

public class GameController {
    private final GameCreateService gameCreateService;
    private final GamePlayService gamePlayService;
    private final GameMapper gameMapper;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameCreateResponse create(@RequestBody @Valid CreateGameRequest request) {
        log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        UUID gameId = gameCreateService.createGame(request.getPlayerName());

        return GameCreateResponse.builder().gameId(gameId).build();
    }

    @PostMapping(path = "/{gameId}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid JoinGameRequest request) throws JoinGameSamePlayerNameException, GameNotFoundException, GameAlreadyFinishedException, JoinFullGameException {
        gameCreateService.joinGame(gameId, request.getPlayerName());
    }

    @PostMapping(path = "/{gameId}/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid MoveRequest request) throws GameNotInProgressException, GameNotFoundException, PlayerAlreadyMadeMoveException {
        gamePlayService.makeMove(gameId, request.getPlayerName(), request.getMove());
    }

    @GetMapping(path = "/{gameId}")
    public GameResultResponse read(@PathVariable("gameId") UUID gameId) throws GameNotFoundException {
        Game game = gamePlayService.getGameResult(gameId);

        return gameMapper.toGameResultResponse(game);
    }
}
