package dev.josiah.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Deck {
    private int deck_id;
    private int owner_id;
    private String deckname;
}
