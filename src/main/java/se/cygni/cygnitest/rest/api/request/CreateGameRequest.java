package se.cygni.cygnitest.rest.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * Date: 07.07.2022
 *
 * @author Nikolay Zinin (nikolay.zinin@gmail.com)
 */
@Getter
@Setter
@ToString
public class CreateGameRequest {
    @NotEmpty
    private String playerName;
}
