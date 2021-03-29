package com.raphaeliinacio.records;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordRepresentation {
    @NotNull
    private Long habitId;
    private Long id;
    @NotNull
    private Long accountId;
    private LocalDateTime date;
    @NotNull
    private Boolean done;

    static Record toDomain(RecordRepresentation recordRepresentation) {
        var record = new Record();
        record.setAccountId(recordRepresentation.getAccountId());
        record.setHabitId(recordRepresentation.getHabitId());
        record.setDone(recordRepresentation.getDone());
        record.setId(recordRepresentation.getId());
        return record;
    }

    static RecordRepresentation toRepresentation(Record record) {
        return RecordRepresentation.builder()
                .accountId(record.getAccountId())
                .habitId(record.getHabitId())
                .done(record.getDone())
                .date(record.getDate())
                .id(record.getId())
                .build();
    }
}
