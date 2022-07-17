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
        UUID gameId = gameCreateService.createGame(request.getName());

        return GameCreateResponse.builder().gameId(gameId).build();
    }

    @PostMapping(path = "/{gameId}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid JoinGameRequest request) throws JoinGameSamePlayerNameException, GameNotFoundException, GameAlreadyFinishedException, JoinFullGameException {
        gameCreateService.joinGame(gameId, request.getName());
    }

    @PostMapping(path = "/{gameId}/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid MoveRequest request) throws GameNotInProgressException, GameNotFoundException, PlayerAlreadyMadeMoveException {
        gamePlayService.makeMove(gameId, request.getName(), request.getMove());
    }

    @GetMapping(path = "/{gameId}")
    public GameResultResponse read(@PathVariable("gameId") UUID gameId) throws GameNotFoundException {
        GameDto gameDto = gamePlayService.getGameResult(gameId);

        return gameMapper.toGameResultResponse(gameDto);
    }
}
