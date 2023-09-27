package com.example.bankingapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bankingapp.Balance;

@Dao
public interface BalanceDao {
    @Insert
    void insert(Balance balance);
    @Update
    void update(Balance balance);
    @Query("select balance from balanceB order by idBalance desc limit 1")
    double getLastBalance();

    @Query("UPDATE balanceB SET balance = balance - :amount WHERE idBalance = (SELECT MAX(idBalance) FROM balanceB)")
    void substractFromLastBalance(double amount);



}
