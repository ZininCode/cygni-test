package se.cygni.cygnitest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder

public class GameExceptionResponse {
    private int statusCode;
    private Date timestamp;
    private UUID gameUuid;
    private String path;
    private String descriptionMessage;
}
