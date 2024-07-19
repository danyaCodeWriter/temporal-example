package ru.danil.trysomething.controller;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import ru.danil.trysomething.dto.CheckDto;

@Tag(name = "Payment controller", description = "Payment controller description | Api Annotation")
public interface PaymentController {
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CheckDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The Tutorial with given Id was not found.", content = {@Content(schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    ResponseEntity<Boolean> deposit(CheckDto check);

    ResponseEntity<Boolean> withdraw(CheckDto check);


}
