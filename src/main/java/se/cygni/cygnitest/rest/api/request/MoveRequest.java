package se.cygni.cygnitest.rest.api.request;

import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitest.dto.MoveEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@Setter

public class MoveRequest {
    @NotEmpty
    private String playerName;
    @NotNull
    private MoveEnum move;
}
