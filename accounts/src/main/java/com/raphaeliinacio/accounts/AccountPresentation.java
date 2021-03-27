package com.raphaeliinacio.accounts;


import com.raphaeliinacio.accounts.client.HabitPresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPresentation {

    private Long id;
    private AccountTypeEnum accountTypeEnum;
    private String name;
    private String email;
    private List<HabitPresentation> myHabits;

    void addHabit(HabitPresentation habitPresentation) {

        if (Objects.isNull(myHabits)) {
            myHabits = new ArrayList<>();
        }

        this.myHabits.add(habitPresentation);
    }

    static AccountPresentation fromDomain(Account accountDomain, List<HabitPresentation> myHabits) {
        return AccountPresentation
                .builder()
                .name(accountDomain.getName())
                .email(accountDomain.getEmail())
                .id(accountDomain.getId())
                .accountTypeEnum(accountDomain.getAccountTypeEnum())
                .myHabits(myHabits)
                .build();
    }
}
