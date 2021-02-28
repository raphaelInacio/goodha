package br.com.goodha.users;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String id;
    private String name;
    private String sunName;
    private String image;
    private String email;
    private LocalDate dateBirth;
    private String familyName;
}
