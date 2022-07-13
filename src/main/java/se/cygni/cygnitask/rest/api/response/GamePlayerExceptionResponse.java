package se.cygni.cygnitask.rest.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder

public class GamePlayerExceptionResponse {
    private int statusCode;
    private Date timestamp;
    private UUID gameUuid;
    String playerName;
    private String path;
    private String descriptionMessage;
}
