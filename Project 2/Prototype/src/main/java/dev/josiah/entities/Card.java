package dev.josiah.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Card {
    private int id;
    private String html_q;
    private String html_a;
}
