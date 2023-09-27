package com.example.bankingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bankingapp.Balance;
import com.example.bankingapp.NewDeposit;

@Database(entities = {Balance.class}, version = 3)
public abstract class BalanceDB extends RoomDatabase {
    public static final String BALANCE_DB_NAME="balance.db";
    public static BalanceDB INSTANCE;

    public abstract BalanceDao getBalanceDao();

    public static BalanceDB getInstance(Context context){
        if(INSTANCE==null){
            synchronized (BalanceDB.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context, BalanceDB.class, BALANCE_DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
