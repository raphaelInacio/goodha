package com.raphaeliinacio.accounts;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPresentation {
    private Long id;
    private AccountTypeEnum accountTypeEnum;
    private String name;
    private String email;
    private List<Long> myHabits;
}
