package com.example.authentication.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@DiscriminatorValue("NON")

public class CarteNonNominative extends Carte{

}
