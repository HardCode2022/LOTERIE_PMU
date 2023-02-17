package com.course.pmu.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Course sur la bse de JPA repo
 */
@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int numero;
    private String nom;
    private LocalDate date;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Partant> partants = new ArrayList<>();

    public Course(int numero, String nom, LocalDate date, List<Partant> partants) {
        this.numero = numero;
        this.nom = nom;
        this.date = date;
        this.partants = partants;
    }

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Partant> getPartants() {
        return partants;
    }

    public void setPartants(List<Partant> partants) {
        this.partants = partants;
    }

    public void addPartant(Partant partant) {
        if (partant == null) {
            throw new IllegalArgumentException("Partant ne doit pas être null");
        }

        if (partants == null) {
            partants = new ArrayList<>();
        }

        partants.add(partant);
        partant.setCourse(this);
    }

    public void removePartant(Partant partant) {
        partants.remove(partant);
        partant.setCourse(null);
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", numero=" + numero +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", partants=" + partants +
                '}';
    }
}
