package com.example.bankingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="deposit")
public class NewDeposit implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double amount;
    private String period;
    private String interestRate;
    private String timeLeft;

    public NewDeposit(){}


    public NewDeposit(String name, double amount, String period, String interestRate, String timeLeft) {
        this.name = name;
        this.amount = amount;
        this.period = period;
        this.interestRate = interestRate;
        this.timeLeft = timeLeft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    @Override
    public String toString() {
        return "DataFromDialog1Class{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", period='" + period + '\'' +
                ", interestRate='" + interestRate + '\'' +
                ", timeLeft='" + timeLeft + '\'' +
                '}';
    }
}
