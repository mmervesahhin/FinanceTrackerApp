package com.ctis487.retrofitjsonobjectwithjsonobjectandarray

import com.google.gson.annotations.SerializedName


//STEP4: Model class created according to JSON object
class Owner (
    @SerializedName("name") //name match with the key of JSON object, GSON according to that name will map the object to data members
    val name: String = "",
    @SerializedName("date")
    val date: String = "")
{
    override fun toString(): String {
        return "Recipe\nName: $name\nvalue=$date\n"
    }
}
