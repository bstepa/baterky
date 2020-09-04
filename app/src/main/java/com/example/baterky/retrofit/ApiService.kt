package com.example.baterky.retrofit

import com.example.baterky.model.Company
import com.example.baterky.model.Ticket
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @POST("ticket")
    fun postTicket(@Body ticket:Ticket): Call<ResponseBody>

    @POST("company")
    fun postCompany(@Body company: Company): Call<ResponseBody>

}