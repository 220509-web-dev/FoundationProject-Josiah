package dev.josiah.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Rating {
    private int card_id;
    private int user_id;
    private Boolean seeagain;
    private double rating;
    private String creationdate;
    private String creationtime;
}
