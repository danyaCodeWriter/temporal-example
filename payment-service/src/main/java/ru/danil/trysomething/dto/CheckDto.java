package ru.danil.trysomething.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Check information")
public class CheckDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "user id from", example = "1")
    @NotNull(message = "Invalid Id: id is NULL")
    private long fromId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "user id to", example = "1")
    @NotNull(message = "Invalid Id: id is NULL")
    private long toId;

    @NotNull(message = "Invalid amount: id is amount")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "amount", example = "1")
    private long amount;
}
