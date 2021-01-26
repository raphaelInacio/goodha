package br.com.goodha.habits;

import lombok.Data;

import java.util.List;


@Data
public class Habit {
    private String name;
    private String longDescription;
    private String image;
    private String longImage;
    private Boolean open = true;
    private List<Record> records;
}
