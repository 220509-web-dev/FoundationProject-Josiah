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

    public Rating(int card_id, int user_id, Boolean seeagain, double rating) {
        this.card_id = card_id;
        this.user_id = user_id;
        this.seeagain = seeagain;
        this.rating = rating;
        this.creationdate = null;
        this.creationtime = null;
    }
}
