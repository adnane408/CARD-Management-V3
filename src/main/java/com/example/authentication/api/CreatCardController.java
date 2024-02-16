package com.example.authentication.api;

import com.example.authentication.Repositories.CarteNomRepository;
import com.example.authentication.Repositories.EmployeRepository;
import com.example.authentication.Repositories.EntrepriseRepository;
import com.example.authentication.Requests.CreatCardNominative;
import com.example.authentication.dto.EmployeDTO;
import com.example.authentication.model.*;
import com.example.authentication.model.Enumerations.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CreatCardController {
    @Autowired
    private CarteNomRepository carteNomRepository;
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    private EmployeDTO mapEmployeToDTO(Employe employe) {
        ModelMapper modelMapper = new ModelMapper();
        EmployeDTO employeDTO = modelMapper.map(employe, EmployeDTO.class);

        // Si vous avez besoin de personnaliser la logique de mapping pour les cartes
        // employeDTO.setCartes(mapCartesToDTO(employe.getCartes()));

        return employeDTO;
    }
    @PostMapping("/create")
    public ResponseEntity<List<Carte>> addMultipleCards(@RequestBody List<CreatCardNominative> cardNominativeList) {
        List<Carte> carteList = new ArrayList<>();

        for (CreatCardNominative creatCardNominative : cardNominativeList) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Check if the user is authenticated
            String username;
            if (authentication != null && authentication.isAuthenticated()) {
                // Retrieve the username from the authentication object
                username = authentication.getName();

                // You can now use the 'username' variable as needed
                System.out.println("Username: " + username);
                Employe employe;
                Entreprise entreprise;

                if (entrepriseRepository.findByUsername(username)==null) {
                    System.out.println("test");
                    return ResponseEntity.notFound().build();

                } else{
                    entreprise = entrepriseRepository.findByUsername(username);
                }

                if (employeRepository.findByIdentite(creatCardNominative.getIdentite()) != null) {
                    employe = employeRepository.findByIdentite(creatCardNominative.getIdentite());
                } else {
                    employe = new Employe(creatCardNominative.getNom(), creatCardNominative.getTele(), creatCardNominative.getIdentite(), Employe.ddn(creatCardNominative.getDdn()), creatCardNominative.getSex(), entreprise);
                    employeRepository.save(employe);
                }
                CarteNominative carteNominative = CarteNominative.builder().employe(employe).build();
                carteNominative.setEntreprise(entreprise);
                carteNominative.setStatus(Status.PREACTIVATED);
                carteNomRepository.save(carteNominative);
                carteList.add(carteNominative);
            }

            }
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{identite}/cartes")
    public ResponseEntity<EmployeDTO> getCard(@PathVariable String identite) {
        Employe employe = employeRepository.findByIdentite(identite);

        if (employe == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeDTO employeDTO = mapEmployeToDTO(employe);

        return ResponseEntity.ok(employeDTO);
    }

}

