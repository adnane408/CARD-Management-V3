package com.example.authentication.Repositories;

import com.example.authentication.model.Employe;
import com.example.authentication.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe,Long> {
    public Employe findByIdentite(String identite);
    public List<Employe> findByEntreprise(Entreprise entreprise);
}
