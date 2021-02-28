package br.com.goodha.habits;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("habitsRepository")
public interface HabitRepository extends DatastoreRepository<Habit, Long> {
    List<Habit> findAll();
}