package com.example.bankingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bankingapp.NewDeposit;

@Database(entities = {NewDeposit.class}, version = 2)
public abstract class DepositDB extends RoomDatabase {
    public static final String DEPOSIT_DB_NAME="deposit.db";
    public static DepositDB INSTANCE;

    public abstract NewDepositDao getNewDepositDao();

    public static DepositDB getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DepositDB.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context, DepositDB.class, DEPOSIT_DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
