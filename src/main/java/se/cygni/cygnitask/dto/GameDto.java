package se.cygni.cygnitask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.cygni.cygnitask.rest.api.response.GameStatus;


import java.util.UUID;


@Getter
@Setter
@Builder
public class GameDto {
    private UUID id;
    private GameStatus status;
    private String winner;
}
