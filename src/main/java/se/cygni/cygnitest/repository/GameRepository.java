package se.cygni.cygnitest.repository;

import org.springframework.stereotype.Repository;
import se.cygni.cygnitest.model.Game;
import se.cygni.cygnitest.rest.api.GameStatus;
import se.cygni.cygnitest.rest.api.MoveEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Repository
public class GameRepository {
    private final Map<UUID, Game> gameMap = new HashMap<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public void addGame(Game game) {
        doInWriteLock(() -> gameMap.put(game.getId(), game));
    }

    public synchronized Optional<Game> findGame(UUID id) { //
       // Game game = doInReadLock(() -> gameMap.get(id));//this is not working (returns null second time of use. I guess it locks and not unlocks.
        // Then I do not know how to unlock in this way with doInReadLock(() -> constructs. I can change it to something else but it will look massive
        // so easier just synchronized
        Game game = gameMap.get(id);

        if (game == null) {
            return Optional.empty();
        }
        return Optional.of(game);
    }

    public void addPlayerToGame(UUID id, String playerName) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setPlayer2(playerName);
            game.setStatus(GameStatus.IN_PROGRESS);
        });
    }

    public void movePlayer1(UUID id, MoveEnum move) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            if (game.getPlayer1Move() != null) {
                throw new IllegalStateException();
            }
            game.setPlayer1Move(move);
        });
    }

    public void movePlayer2(UUID id, MoveEnum move) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            if (game.getPlayer2Move() != null) {
                throw new IllegalStateException();
            }
            game.setPlayer2Move(move);
        });
    }

    public void makeDraw(UUID id) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setStatus(GameStatus.DRAW);
        });
    }

    public void makePlayer1Wins(UUID id) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setStatus(GameStatus.HAS_WINNER);
            game.setWinner(game.getPlayer1());
        });
    }

    public void makePlayer2Wins(UUID id) {
        doInWriteLock(() -> {
            Game game = gameMap.get(id);
            game.setStatus(GameStatus.HAS_WINNER);
            game.setWinner(game.getPlayer2());
        });
    }

    private void doInWriteLock(Runnable runnable) {
        Lock writeLock = readWriteLock.writeLock();
        try {
            writeLock.lock();

            runnable.run();
        } finally {
            writeLock.unlock();
        }
    }

    private <T> T doInReadLock(Supplier<T> supplier) {
        Lock writeLock = readWriteLock.writeLock();
        try {
            writeLock.lock();

            return supplier.get();
        } finally {
            writeLock.unlock();
        }
    }

}
