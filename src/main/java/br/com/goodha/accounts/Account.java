package br.com.goodha.accounts;

import br.com.goodha.habits.Habit;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;

import java.util.List;

@Entity
public class Account {
    private Long id;
    private AccountTypeEnum accountTypeEnum;
    private String name;
    private String email;
    private List<Habit> myHabits;
}
