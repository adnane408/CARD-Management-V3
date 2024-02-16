package com.example.authentication.dto;

import com.example.authentication.model.*;
import com.example.authentication.model.Enumerations.Status;
import com.example.authentication.model.Enumerations.TypeOperation;
import lombok.Data;

import java.util.Date;

@Data
public class OperationDto {
    private TypeOperation typeOperation;
    private String description;
    private double montant;
    private Date date;
    private String numeroCarte;
    private String entreprise;
    private Double balance;
    private String nomPerson;
    private String personId;
    private Status status;

    public OperationDto(Operation operation, CarteNominative carte) {
        this.typeOperation = operation.getTypeOperation();
        this.description = operation.getDescription();
        this.montant = operation.getMontant();
        this.date = operation.getDate();
        this.numeroCarte = carte.getId();
        this.entreprise = carte.getEntreprise().getNom();
        this.balance = carte.getEntreprise().getBalance();
        this.nomPerson = carte.getEmploye().getNomComplet();
        this.personId = carte.getEmploye().getIdentite();
        this.status = carte.getStatus();
    }
}