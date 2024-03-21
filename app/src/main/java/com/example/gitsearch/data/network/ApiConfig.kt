package com.example.gitsearch.data.network

import com.example.gitsearch.data.network.Constant.API_KEY
import com.example.gitsearch.data.network.Constant.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader(
                    "Authorization",
                    API_KEY
                )
                .build()
            chain.proceed(requestHeaders)
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}