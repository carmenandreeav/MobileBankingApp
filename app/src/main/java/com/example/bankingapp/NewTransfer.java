package com.example.bankingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "newtransfer")
public class NewTransfer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    double suma;
    String beneficiar;
    String iban;
    String descriere;
    String dataTranzactie;
    boolean venit;
    boolean factura;
    String category;

    public NewTransfer(){}

    public NewTransfer(double suma, String beneficiar, String iban, String descriere, String dataTranzactie, boolean venit, boolean factura, String category) {
        this.suma = suma;
        this.beneficiar = beneficiar;
        this.iban = iban;
        this.descriere = descriere;
        this.dataTranzactie = dataTranzactie;
        this.venit = venit;
        this.factura = factura;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }

    public boolean isVenit() {
        return venit;
    }

    public void setVenit(boolean venit) {
        this.venit = venit;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public String getBeneficiar() {
        return beneficiar;
    }

    public void setBeneficiar(String beneficiar) {
        this.beneficiar = beneficiar;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDataTranzactie() {
        return dataTranzactie;
    }

    public void setDataTranzactie(String dataTranzactie) {
        this.dataTranzactie = dataTranzactie;
    }

    @Override
    public String toString() {
        return "NewTransfer{" +
                "id=" + id +
                ", suma=" + suma +
                ", beneficiar='" + beneficiar + '\'' +
                ", iban='" + iban + '\'' +
                ", descriere='" + descriere + '\'' +
                ", dataTranzactie='" + dataTranzactie + '\'' +
                ", venit=" + venit +
                ", factura=" + factura +
                ", category='" + category + '\'' +
                '}';
    }
}
