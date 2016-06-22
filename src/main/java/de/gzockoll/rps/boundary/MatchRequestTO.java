package de.gzockoll.rps.boundary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by guido on 21.06.16.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequestTO {
    private String gameId;
    private String choice;
}
