package com.raphaeliinacio.accounts.repository;

import com.raphaeliinacio.accounts.client.HabitPresentation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface HabitRepository {
    HabitPresentation getHabitById(@PathVariable("id") Long id);
    List<HabitPresentation> allHabitsByIds(@RequestBody List<Long> ids);
}
