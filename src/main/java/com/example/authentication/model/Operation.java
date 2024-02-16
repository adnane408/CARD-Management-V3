package com.example.authentication.model;

import com.example.authentication.model.Enumerations.TypeOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;
    private String description;
    private double montant;
    private Date date;

    public Operation(TypeOperation typeOperation, String description, double montant, Date date) {
        this.typeOperation = typeOperation;
        this.description = description;
        this.montant = montant;
        this.date = date;
    }
}
