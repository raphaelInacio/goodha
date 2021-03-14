package com.raphaeliinacio.accounts;


import lombok.Data;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
public class Account {
    @Id
    private Long id;
    private AccountTypeEnum accountTypeEnum;
    private String name;
    private String email;
    private List<Long> myHabits;
}
