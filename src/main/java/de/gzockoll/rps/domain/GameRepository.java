package de.gzockoll.rps.domain;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by guido on 21.06.16.
 */
@Repository
public class GameRepository {
    private Map<String, Game> games = new ConcurrentHashMap<>();

    public void save(Game aGame) {
        games.put(aGame.getId(), aGame);
    }

    public Optional<Game> findById(String id) {
        return Optional.ofNullable(games.get(id));
    }
}
