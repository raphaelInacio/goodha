package br.com.goodha.habits;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Record {
    private LocalDateTime date;
    private Boolean done = false;
}
