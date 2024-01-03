package com.example.financetrackerapp_ctis487_team6.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ctis487.retrofit.Constants


@Dao
interface CustomerDAO {
    // The conflict strategy defines what happens,if there is an existing entry.
    // The default action is ABORT.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Update
    fun updateCustomer(customer: Customer)

    @Delete
    fun deleteCustomer(customer: Customer)

    @Query("DELETE FROM ${Constants.TABLENAME}")
    fun deleteAllCustomers()

    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY id DESC")
    fun getAllCustomers():MutableList<Customer>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE id =:id")
    fun getCustomerById(id:Int):Customer

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE name LIKE :name")
    fun getCustomersByName(name:String):MutableList<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCustomer(customers: ArrayList<Customer>){
        customers.forEach{
            insertCustomer(it)
        }
    }

    @Query("SELECT SUM(debt) FROM ${Constants.TABLENAME}")
    fun getTotalDebt(): Double
}