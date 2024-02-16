package com.example.authentication.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

public class CustomIDGenerator implements IdentifierGenerator {

    private static final int ID_LENGTH = 16;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return generateCustomID();
    }

    private String generateCustomID() {
        StringBuilder stringBuilder = new StringBuilder(ID_LENGTH);

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < ID_LENGTH; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit (0-9)
            stringBuilder.append(randomDigit);
        }

        return stringBuilder.toString();
    }
}
