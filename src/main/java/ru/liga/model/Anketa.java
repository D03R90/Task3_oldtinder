package ru.liga.model;

import lombok.*;
import javax.persistence.*;

@Table(name = "old_tinder")
@Entity
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Anketa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String person_name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "description")
    private String description;
    @Column(name = "chat_id")
    private String chat_id;
}
