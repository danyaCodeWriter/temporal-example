package ru.danil.trysomething.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import ru.danil.trysomething.dto.UserDto;
import ru.danil.trysomething.entity.User;
import ru.danil.trysomething.statuses.EventUserStatus;

@Tag(name = "User controller", description = "User controller description | Api Annotation")
public interface UserController {
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The Tutorial with given Id was not found.", content = {@Content(schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    ResponseEntity<User> createFullUser(UserDto user);

    ResponseEntity<UserDto> get(@Parameter(description = "id") long id);

    ResponseEntity<String> setStatus(@Parameter(description = "id") long id, @Parameter(description = "status") EventUserStatus status);

}
