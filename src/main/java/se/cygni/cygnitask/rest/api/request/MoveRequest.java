package se.cygni.cygnitask.rest.api.request;

import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitask.rest.api.response.MoveEnum;

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
    private String name;
    @NotNull
    private MoveEnum move;
}
