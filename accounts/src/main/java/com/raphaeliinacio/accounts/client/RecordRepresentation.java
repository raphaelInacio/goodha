package com.raphaeliinacio.accounts.client;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordRepresentation {
    @NotNull
    private Long habitId;
    private Long id;
    @NotNull
    private Long accountId;
    private LocalDateTime date;
    @NotNull
    private Boolean done;
}
