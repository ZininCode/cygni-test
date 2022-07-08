package se.cygni.cygnitest.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.cygni.cygnitest.dto.GameDto;
import se.cygni.cygnitest.exception.*;
import se.cygni.cygnitest.mapper.GameMapper;
import se.cygni.cygnitest.rest.api.request.CreateGameRequest;
import se.cygni.cygnitest.rest.api.request.JoinGameRequest;
import se.cygni.cygnitest.rest.api.request.MoveRequest;
import se.cygni.cygnitest.rest.api.response.CreateGameResponse;
import se.cygni.cygnitest.rest.api.response.GameResultResponse;
import se.cygni.cygnitest.service.GameService;

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
public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateGameResponse create(@RequestBody @Valid CreateGameRequest request) {
        UUID gameId = gameService.createGame(request.getPlayerName());

        return CreateGameResponse.builder().gameId(gameId).build();
    }

    @PostMapping(path = "/{gameId}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid JoinGameRequest request) throws JoinGameException, GameNotFoundException, GameAlreadyStartedException {
        gameService.joinGame(gameId, request.getPlayerName());
    }

    @PostMapping(path = "/{gameId}/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid MoveRequest request) throws GameNotInProgressException, GameNotFoundException, PlayerAlreadyMakeMoveException {
        gameService.makeMove(gameId, request.getPlayerName(), request.getMove());
    }

    @GetMapping(path = "/{gameId}")
    public GameResultResponse read(@PathVariable("gameId") UUID gameId) throws GameNotFoundException {
        GameDto gameDto = gameService.getGameResult(gameId);

        return gameMapper.toGameResultResponse(gameDto);
    }
}
