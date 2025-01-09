package fr.uga.miage.m1.endpoint.blocking;


import fr.uga.miage.m1.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/users")
public interface UserEndpoints {

    @Operation(description = "create user")
    @ApiResponse(responseCode = "201", content = @Content(
            schema = @Schema(implementation = UserResponse.class)
    ))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{pseudo}")
    UserResponse createUser(@PathVariable String pseudo);

    @Operation(description = "get by pseudo")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = UserResponse.class)
    ))
    @ApiResponse(description = "not found", responseCode = "404")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{pseudo}")
    UserResponse getByPseudo(@PathVariable String pseudo);

    @Operation(description = "get all")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = UserResponse.class)))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    Collection<UserResponse> getAll();

}
