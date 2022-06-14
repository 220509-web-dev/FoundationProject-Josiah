package dev.josiah.entities;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CardDeck {
    private List<Integer> card_ids;
    private int deck_id;
}
