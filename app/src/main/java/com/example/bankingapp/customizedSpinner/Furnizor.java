package com.example.bankingapp.customizedSpinner;

import java.io.Serializable;

public class Furnizor implements Serializable {
    private String nume;

    public Furnizor(){

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Furnizor{" +
                "nume='" + nume + '\'' +
                '}';
    }
}

