package com.example.authentication.Repositories;

import com.example.authentication.model.Employe;
import com.example.authentication.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntrepriseRepository extends JpaRepository<Entreprise,Long> {
    public Entreprise findByNom(String nom);
    public Entreprise findByUsername(String username);
}
