package com.example.bankingapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bankingapp.NewDeposit;
import com.example.bankingapp.NewTransfer;

import java.util.List;

@Dao
public interface NewTransferDAO {

    @Insert
    void addTransfer(NewTransfer newTransfer);

    @Query("select * from newtransfer")
    List<NewTransfer> getAllTransfers();

    @Delete
    void deleteTransfer(NewTransfer newTransfer);

//    @Delete
//    void deleteALL(List<NewTransfer> newTransferList);

    @Query("DELETE FROM newtransfer")
    void deleteAllTransfers();
}
