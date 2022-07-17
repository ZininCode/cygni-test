package se.cygni.cygnitask.rest.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@Setter

public class CreateGameRequest {
    @NotEmpty
    private String name;
}
