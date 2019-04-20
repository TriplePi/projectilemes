package com.triplepi.projectilemes.data.network

import com.triplepi.projectilemes.data.network.dto.ProductDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("api/products/{productId}")
    fun getProduct(@Path("productId")id: Long): Call<ProductDto>
}