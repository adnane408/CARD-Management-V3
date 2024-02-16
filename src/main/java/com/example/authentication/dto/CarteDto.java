package com.example.authentication.dto;

import com.example.authentication.model.*;
import com.example.authentication.model.Enumerations.Status;
import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class CarteDto {

    private String id;
    private float solde;

    private String entreprise;

    private Status status;
    private String employeId;
    private String employeName;


    public CarteDto(CarteNominative carte) {
        this.id = carte.getId();
        this.solde = carte.getSolde();
        this.status = carte.getStatus();
        this.employeId = carte.getEmploye().getIdentite();
        this.employeName = carte.getEmploye().getNomComplet();
        this.entreprise=carte.getEntreprise().getNom();

    }


    public CarteDto(Carte carte) {
        this.id = carte.getId();
        this.solde = carte.getSolde();
        this.status = carte.getStatus();
    }
}
