package ru.danil.trysomething.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "User information")
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "user id", example = "1")
    private Long id;

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "user name", example = "danil")
    private String username;


    private String password;

    @NotBlank(message = "Invalid email structure: Empty number")
    @NotNull(message = "Invalid email structure: Number is NULL")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email structure number")
    private String email;

    @NotBlank(message = "Invalid status!")
    private String status;

    @Min(value = 1, message = "Invalid Age: Equals to zero or Less than zero")
    @Max(value = 100, message = "Invalid Age: Exceeds 100 years")
    private int age;
    private List<AddressDto> address;
    private List<RoleDto> roles;
}
