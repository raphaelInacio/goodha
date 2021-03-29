package com.raphaeliinacio.records;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Entity(name = "Habits")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    @Id
    private Long id;
    private Long habitId;
    private Long accountId;
    private LocalDateTime date = LocalDateTime.now();
    private Boolean done = false;
}
