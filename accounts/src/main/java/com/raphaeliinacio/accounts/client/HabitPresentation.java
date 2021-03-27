package com.raphaeliinacio.accounts.client;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HabitPresentation {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String longDescription;
    @NotBlank
    private String image;
    @NotBlank
    private String longImage;
}
