package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Validated
public class User {

    @Min(1)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

}