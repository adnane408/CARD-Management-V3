package com.example.authentication.Requests;

import com.example.authentication.model.Enumerations.TypeOperation;
import lombok.Data;

@Data
public class AddOperation {
    private String cardNumber;
    private String employeeID;
    private String  employeeName;
    private TypeOperation typeOperation;
    private String motifOperation;
    private double montant;

    @Override
    public String toString() {
        return "AddOperation{" +
                "cardNumber='" + cardNumber + '\'' +
                ", employeeID='" + employeeID + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", typeOperation=" + typeOperation +
                ", motifOperation='" + motifOperation + '\'' +
                ", montant=" + montant +
                '}';
    }
}
