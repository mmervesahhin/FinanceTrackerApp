package com.ctis487.retrofit

import com.ctis487.retrofitjsonobjectwithjsonobjectandarray.Parent
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {
    @GET("posts")
    fun getCurrencies(): Call<Parent>

}