package com.raphaeliinacio.accounts.controller;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddHabitPresentation {

    @NotNull
    private Long id;
}
