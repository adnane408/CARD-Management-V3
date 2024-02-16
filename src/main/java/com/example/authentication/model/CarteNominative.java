package com.example.authentication.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("NOMI")

public class CarteNominative extends Carte{
    @ManyToOne
    private Employe employe;


    @Override
    public String toString() {
        return "CarteNominative{" +
                super.toString() +
                "employe=" + employe.getNomComplet() +
                '}';
    }
}
