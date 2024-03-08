package com.course.pmu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity partant
 */
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Partant implements Serializable {

    private static final long serializable = 5647836453783864877L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PARTANT")
    private Long id;
    @Column(name = "NUMERO_PARTANT")
    private int numero;
    @Column(name = "NOM_PARTANT")
    private String nom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMU_COURSE", referencedColumnName = "ID_COURSE", nullable = true)
    private Course course;
}
