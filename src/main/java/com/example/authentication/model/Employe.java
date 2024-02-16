package com.example.authentication.model;

import com.example.authentication.model.Enumerations.Sex;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employe {
    @Id
    @GeneratedValue
    private Long id;
    private String nomComplet;
    private String tele;
    @Column(unique = true)
    private String identite;
    private Date ddn;
    private Sex sex;
    @OneToMany(mappedBy = "employe", fetch = FetchType.EAGER)
    private Collection<CarteNominative> cartesNominatives;
    @ManyToOne
    @JsonManagedReference
    private Entreprise entreprise;

    public Employe(String nomComplet, String identite, Date ddn, Sex sex) {
        this.nomComplet =nomComplet;
        this.identite = identite;
        this.ddn = ddn;
        this.sex = sex;
    }

    public Employe(String nomComplet, String tele, String identite, Date ddn, Sex sex, Entreprise entreprise) {
        this.nomComplet =nomComplet;
        this.tele = tele;
        this.identite = identite;
        this.ddn = ddn;
        this.sex = sex;
        this.entreprise = entreprise;
    }

    public static Date ddn(String ddnString) {
        Date ddn = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ddn = dateFormat.parse(ddnString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ddn;
    }
}
