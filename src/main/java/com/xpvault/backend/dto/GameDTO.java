package com.xpvault.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameDTO {

    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String description;

    @Digits(integer = 9, fraction = 0, message = "Steam ID must contain only digits (max 10 digits)")
    private Integer steamId;

}
