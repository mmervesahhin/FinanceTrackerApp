package com.example.financetrackerapp_ctis487_team6.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//If you change anything on the database like adding a field to table, chnaging type of a filed, deleting a filed, changing the name of the field
@Database(entities = [Customer::class], version = 4)
abstract class CustomerRoomDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDAO
 

}
