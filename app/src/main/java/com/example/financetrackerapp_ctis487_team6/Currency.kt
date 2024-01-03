package com.ctis487.retrofit

import com.google.gson.annotations.SerializedName
//STEP4: Model class created according to JSON object
class Currency (
    @SerializedName("name") //name match with the key of JSON object, GSON according to that name will map the object to data members
    val name: String = "",
    @SerializedName("value")
    val value: String = "",
    @SerializedName("details")
    val details: String = "",
    @SerializedName("img")
    val img: String = "")
{
    override fun toString(): String {
        return "Currency(name='$name', value='$value', details='$details')"
    }

}

