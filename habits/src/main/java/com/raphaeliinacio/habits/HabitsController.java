package com.raphaeliinacio.habits;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/habits")
@Slf4j
@Transactional
public class HabitsController {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<HabitPresentation>> listHabits() {
        log.info("..: Listando todos os habitos");
        var habits = habitRepository.findAll();

        var habitPresentations = habits.stream()
                .filter(Objects::nonNull)
                .map(habit -> modelMapper.map(habit, HabitPresentation.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(habitPresentations);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HabitPresentation> getHabitById(@PathVariable Long id) {
        var habit = habitRepository.findById(id);

        if (habit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelMapper.map(habit.get(), HabitPresentation.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitPresentation> update(@RequestBody HabitPresentation presentation, @PathVariable Long id) {
        log.info("..: Atualizando um novo habito, {}, com id, {}", presentation, id);
        var habit = habitRepository.findById(id);

        if (habit.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        var uptdatedHabit = modelMapper.map(presentation, Habit.class);
        uptdatedHabit.setId(habit.get().getId());

        uptdatedHabit = habitRepository.save(modelMapper.map(presentation, Habit.class));
        return ResponseEntity.ok(modelMapper.map(uptdatedHabit, HabitPresentation.class));
    }

    @PostMapping
    public ResponseEntity<HabitPresentation> saveHabit(@RequestBody @Valid HabitPresentation presentation) {
        log.info("..: Criando um novo habito");
        var newHabit = habitRepository.save(modelMapper.map(presentation, Habit.class));
        return ResponseEntity.ok(modelMapper.map(newHabit, HabitPresentation.class));
    }
}
