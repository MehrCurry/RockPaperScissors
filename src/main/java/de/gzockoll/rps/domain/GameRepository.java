package de.gzockoll.rps.domain;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * = GameRepository
 * Simulates some kind of backend storage. Currently the games are
 * stored in a `ConcurrentHashMap`.
 */
@Repository
public class GameRepository {
    private Map<String, Game> games = new ConcurrentHashMap<>();

    public void save(Game aGame) {
        games.put(aGame.getId(), aGame);
    }

    /**
     * @param id of a game to search for
     * @return to avoid null pointer exceptions we use an `Opional<Game>` as return value
     * to make clear that the method will possibly return no `Game` at all.
     */
    public Optional<Game> findById(String id) {
        return Optional.ofNullable(games.get(id));
    }
}
