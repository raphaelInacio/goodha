package com.raphaeliinacio.records;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1/records")
@Slf4j
public class RecordsController {

    @Autowired
    private RecordRepository repository;


    @PostMapping
    public ResponseEntity<RecordRepresentation> createRecord(@RequestBody RecordRepresentation recordRepresentation) {
        log.info("..: Criando um novo registro");
        Record saveRecord = repository.save(RecordRepresentation.toDomain(recordRepresentation));
        log.info("..: Registo salvo: {}", saveRecord);
        return ResponseEntity.ok(RecordRepresentation.toRepresentation(saveRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordRepresentation> updateRecord(@PathVariable("id") Long id, @RequestBody RecordRepresentation recordRepresentation) {
        log.info("..: Atualizando um registro {}, {}", id, recordRepresentation);

        Optional<Record> byId = repository.findById(id);

        if (byId.isEmpty()) {
            log.info("..: Não existem records para: {}, {}", id, recordRepresentation);
            return ResponseEntity.noContent().build();
        }

        Record record = RecordRepresentation.toDomain(recordRepresentation);
        record.setId(byId.get().getId());

        Record saveRecord = repository.save(record);
        log.info("..: Registo salvo: {}", saveRecord);

        return ResponseEntity.ok(RecordRepresentation.toRepresentation(saveRecord));
    }

    @GetMapping("/{accountId}/{habitId}")
    public ResponseEntity<List<RecordRepresentation>> getRecordsByAccountAndHabitId(@PathVariable("accountId") Long accountId, @PathVariable("habitId") Long habitId) {
        log.info("..: Buscando records: {}, {}", accountId, habitId);

        var records = repository.findByAccountIdAndHabitId(accountId, habitId);

        if (records.isEmpty()) {
            log.info("..: Não existem records para: {}, {}", accountId, habitId);
            return ResponseEntity.noContent().build();
        }

        log.info("..: Records encontrados: {}", records);

        return ResponseEntity.ok(records.stream()
                .map(record -> RecordRepresentation.toRepresentation(record))
                .collect(Collectors.toList()));
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<List<RecordRepresentation>> getRecordsByAccount(@PathVariable("accountId") Long accountId) {
        log.info("..: Buscando records por account: {}", accountId);

        var records = repository.findByAccountId(accountId);

        if (records.isEmpty()) {
            log.info("..: Não existem records para account: {}", accountId);
            return ResponseEntity.noContent().build();
        }

        log.info("..: Records encontrados: {}", records);

        return ResponseEntity.ok(records.stream()
                .map(record -> RecordRepresentation.toRepresentation(record))
                .collect(Collectors.toList()));
    }
}
