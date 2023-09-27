package com.example.bankingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="balanceB")
public class Balance implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int idBalance;
    public double balance;

    public Balance(){}

    public Balance(int idBalance, double balance) {
        this.idBalance = idBalance;
        this.balance = balance;
    }

    public int getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(int idBalance) {
        this.idBalance = idBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "balance=" + balance +
                '}';
    }
}
