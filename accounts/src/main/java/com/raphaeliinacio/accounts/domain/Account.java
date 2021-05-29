package com.raphaeliinacio.accounts.domain;


import lombok.Data;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
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

    public void addHabit(Long habitId) {
        if (myHabits == null) {
            myHabits = new ArrayList<>();
        }
        myHabits.add(habitId);
    }
}
