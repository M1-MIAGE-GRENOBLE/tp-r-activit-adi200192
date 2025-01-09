package fr.uga.miage.m1.endpoint.reactive;


import fr.uga.miage.m1.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("reactive/users")
public interface ReactiveUserEndpoints {

    @Operation(description = "create user")
    @ApiResponse(responseCode = "201", content = @Content(
            schema = @Schema(implementation = UserResponse.class)
    ))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{pseudo}")
    Mono<UserResponse> createUser(@PathVariable String pseudo);

    @Operation(description = "get by pseudo")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = UserResponse.class)
    ))
    @ApiResponse(description = "not found", responseCode = "404")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{pseudo}")
    Mono<UserResponse> getByPseudo(@PathVariable String pseudo);

    @Operation(description = "get all")
    @ApiResponse(responseCode = "200", content = @Content(
            mediaType = MediaType.TEXT_EVENT_STREAM_VALUE,
            schema = @Schema(implementation = UserResponse.class)))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    Flux<UserResponse> getAll();

}
