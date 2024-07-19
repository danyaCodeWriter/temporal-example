package ru.danil.trysomething.dto;

import lombok.Data;

@Data
public class RoleDto {

    private Long id;

    private String name;

    private String description;

    private String permissions;
}
