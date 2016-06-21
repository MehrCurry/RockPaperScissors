package de.gzockoll.rps.boundary;

import de.gzockoll.rps.domain.GameResult;
import lombok.Builder;
import lombok.Data;

/**
 * Created by guido on 21.06.16.
 */
@Data
@Builder
public class ResultTO {
    private final String gameId;
    private final String yourChoice;
    private final String opponentsChoice;
    private final GameResult result;
}
