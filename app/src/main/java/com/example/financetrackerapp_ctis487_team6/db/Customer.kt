package com.example.financetrackerapp_ctis487_team6.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ctis487.retrofit.Constants


@Entity(tableName = Constants.TABLENAME)
class Customer(
    @PrimaryKey(autoGenerate = false)
    var id:String="",
    var name: String,
    @ColumnInfo(name = "lastname") var surname: String,
    var debt: Double)
{
    override fun toString(): String {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", debt=" + debt +
                '}'
    }
}