package com.ctis487.retrofitjsonobjectwithjsonobjectandarray

import com.ctis487.retrofit.Currency
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Parent (
    @SerializedName("owner")
    @Expose
    var owner: Owner?,
    @SerializedName("currency")
    var currencies: List<Currency>?)
    {
        override fun toString(): String {
            return "${owner.toString()} ${currencies.toString()}\n"
        }
    }