package com.example.bankingapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bankingapp.NewDeposit;

import java.util.List;

@Dao
public interface NewDepositDao {

    @Insert
    void addDeposit(NewDeposit newDeposit);

    @Query("select * from deposit")
    List<NewDeposit> getAllDeposits();

    @Delete
    void deleteDeposit(NewDeposit newDeposit);




}
