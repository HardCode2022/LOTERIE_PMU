package com.course.pmu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Course sur la base de JPA repo
 */
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {

    private static final long serializable = 564785677555864877L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_COURSE", unique = true)
    private Long id;
    @Column(name = "NUMERO_COURSE")
    private int numero;
    @Column(name = "NOM_COURSE")
    private String nom;
    @Column(name = "DATE_COURSE")
    private LocalDate date;
    //@OneToMany(mappedBy = "COURSE", cascade = CascadeType.ALL)
    //@ToString.Exclude
    @Transient
    private List<Partant> partants = new ArrayList<>();
}
