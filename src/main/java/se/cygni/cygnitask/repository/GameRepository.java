package se.cygni.cygnitask.repository;

import org.springframework.stereotype.Repository;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * The game repository that stores data of the games in memory and giving access to them.
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin
 */
@Repository
public class GameRepository {
    private final Map<UUID, Game> gameMap = new HashMap<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * Adding game to game map.
     * @param game The game object.
     */
    public void addGame(Game game) {
        doInWriteLock(() -> gameMap.put(game.getId(), game));
    }

    /**
     * Finding game by id.
     * @param id The game id.
     * @return The game (Optional).
     */
    public Optional<Game> findGame(UUID id) {
        Game game = doInReadLock(() -> gameMap.get(id));
        if (game == null) {
            return Optional.empty();
        }
        return Optional.of(game);
    }

    /**
     * Adding a second player to the game.
     * @param id The game id.
     * @param playerName A name of the player.
     */
    public void addPlayerToGame(UUID id, String playerName) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setPlayer2(playerName);
            game.setStatus(GameStatus.IN_PROGRESS);
        });
    }

    /**
     * Set first player move to the game.
     * @param id The game id.
     * @param move The player's move.
     */
    public void player1Move(UUID id, MoveEnum move) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            if (game.getPlayer1Move() != null) {
                throw new IllegalStateException();
            }
            game.setPlayer1Move(move);
        });
    }

    /**
     * Set the second player move to the game.
     * @param id The game id.
     * @param move The player's move.
     */
    public void player2Move(UUID id, MoveEnum move) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            if (game.getPlayer2Move() != null) {
                throw new IllegalStateException();
            }
            game.setPlayer2Move(move);
        });
    }

    /**
     * Set the game status as "DRAW".
     * @param id The game id.
     */
    public void makeDraw(UUID id) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setStatus(GameStatus.DRAW);
        });
    }

    /**
     * Set the game status as "HAS_WINNER" and set the first player's name as a winner of the game.
     * @param id The game id.
     */
    public void makePlayer1Wins(UUID id) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setStatus(GameStatus.HAS_WINNER);
            game.setWinner(game.getPlayer1());
        });
    }

    /**
     *  Set the game status as "HAS_WINNER" and set the second player's name as a winner of the game.
     *
     * @param id The game id.
     */
    public void makePlayer2Wins(UUID id) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setStatus(GameStatus.HAS_WINNER);
            game.setWinner(game.getPlayer2());
        });
    }

    /**
     * A method used to do access (write) to the map of games synchronized.
     * @param runnable
     */
    private void doInWriteLock(Runnable runnable) {
        Lock writeLock = readWriteLock.writeLock();
        try {
            writeLock.lock();

            runnable.run();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     *  A method used to do access (read) to the map of games synchronized.
     * @param supplier
     * @param <T>
     * @return
     */
    private <T> T doInReadLock(Supplier<T> supplier) {
        Lock readLock = readWriteLock.writeLock();
        try {
            readLock.lock();

            return supplier.get();
        } finally {
            readLock.unlock();
        }
    }
}
