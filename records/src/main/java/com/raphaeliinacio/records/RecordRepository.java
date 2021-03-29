package com.raphaeliinacio.records;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends DatastoreRepository<Record, Long> {
    List<Record> findByAccountIdAndHabitId(Long accountId, Long habitId);

    List<Record> findByAccountId(Long accountId);
}
