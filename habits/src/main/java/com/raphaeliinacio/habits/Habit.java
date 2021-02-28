package br.com.goodha.habits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;


@Data
@Entity(name = "Habits")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    @Id
    private Long id;
    private Long accountId;
    private String name;
    private String longDescription;
    private String image;
    private String longImage;
}
