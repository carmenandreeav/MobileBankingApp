package com.example.bankingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bankingapp.NewTransfer;

@Database(entities = {NewTransfer.class}, version = 3)
public abstract class NewTransferDP extends RoomDatabase {
    public static final String NEWTRANSFER_DB_NAME="transfer.db";
    public static NewTransferDP INSTANCE;

    public abstract NewTransferDAO getNewTransferDAO();

    public static NewTransferDP getInstance(Context context){
        if(INSTANCE==null){
            synchronized (NewTransferDP.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context, NewTransferDP.class, NEWTRANSFER_DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
