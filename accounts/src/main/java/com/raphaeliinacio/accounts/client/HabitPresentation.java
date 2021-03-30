package com.raphaeliinacio.accounts.client;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class HabitPresentation {

    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String longDescription;
    @NotBlank
    private String image;
    @NotBlank
    private String longImage;

    private List<RecordRepresentation> recordRepresentations;

    public void addRecords(List<RecordRepresentation> recordRepresentations) {

        if (this.recordRepresentations == null) {
            this.recordRepresentations = new ArrayList<>();
        }

        this.recordRepresentations.addAll(recordRepresentations);
    }
}
