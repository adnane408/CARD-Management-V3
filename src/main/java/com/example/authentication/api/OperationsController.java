package com.example.authentication.api;

import com.example.authentication.Repositories.CarteNomRepository;
import com.example.authentication.Repositories.EntrepriseRepository;
import com.example.authentication.Repositories.OperationRepository;
import com.example.authentication.Requests.AddOperation;
import com.example.authentication.dto.OperationDto;
import com.example.authentication.model.Carte;
import com.example.authentication.model.CarteNominative;
import com.example.authentication.model.Entreprise;
import com.example.authentication.model.Enumerations.TypeOperation;
import com.example.authentication.model.Operation;
import com.example.authentication.service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationsController {
    @Autowired
    private CarteNomRepository carteNomRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @PostMapping("/add")
    public ResponseEntity<?> addOperation(@RequestBody AddOperation addOperation){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=null;
        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve the username from the authentication object
            username = authentication.getName();

            // You can now use the 'username' variable as needed
            System.out.println("Username: " + username);}
        Entreprise entreprise;

        if (entrepriseRepository.findByUsername(username) == null) {
            System.out.println("Entreprise not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entreprise not found");
        } else {
            entreprise = entrepriseRepository.findByUsername(username);
        }

        CarteNominative carteNominative = carteNomRepository.findById(addOperation.getCardNumber()).orElse(null);

        if (carteNominative == null) {
            System.out.println("Carte not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carte not found");
        }
        Operation operation=new Operation(addOperation.getTypeOperation(),
                addOperation.getMotifOperation(),
                addOperation.getMontant(),
                new Date());
        operationRepository.save(operation);
        carteNominative.addOperation(operation);
        if(addOperation.getTypeOperation()== TypeOperation.CREDIT ) {
            if(entreprise.getBalance()<addOperation.getMontant()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("balance insuffisante");
            }
            entreprise.setBalance(entreprise.getBalance()- addOperation.getMontant());
            carteNominative.setSolde((float) (carteNominative.getSolde()+addOperation.getMontant()));
        }else {
            if(carteNominative.getSolde() <addOperation.getMontant()){
                carteNominative.setSolde(0);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vous avez depasser le solde");
            }
            entreprise.setBalance(entreprise.getBalance()+addOperation.getMontant());
        }
        carteNomRepository.save(carteNominative);
        entrepriseRepository.save(entreprise);
        OperationDto operationDto=new OperationDto(operation,carteNominative);
        return ResponseEntity.ok(operationDto);
    }
}
