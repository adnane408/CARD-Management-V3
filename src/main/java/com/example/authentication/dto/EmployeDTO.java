package com.example.authentication.dto;

import java.util.Collection;

// EmployeDTO.java
public class EmployeDTO {
    private String identite;
    private Collection<CarteNominativeDTO> cartes;

    public String getIdentite() {
        return identite;
    }

    public void setIdentite(String identite) {
        this.identite = identite;
    }

    public Collection<CarteNominativeDTO> getCartes() {
        return cartes;
    }

    public void setCartes(Collection<CarteNominativeDTO> cartes) {
        this.cartes = cartes;
    }
}

// CarteNominativeDTO.java


