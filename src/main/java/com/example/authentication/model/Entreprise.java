package com.example.authentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Entreprise {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String nom;
    private String username;
    @Column(unique = true)
    public String ConventionID;
    private Double balance;
    @OneToMany(mappedBy = "entreprise")
    private List<Carte> cartes=new ArrayList<>();
    @OneToMany(mappedBy = "entreprise", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Employe> employes=new ArrayList<>();

    public Entreprise(String nom, String username, String conventionID) {
        this.nom = nom;
        this.username = username;
        ConventionID = conventionID;
    }
}
