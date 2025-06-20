package com.br.moviefy.data.network.interceptor

import android.util.Log
import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getToken()

        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrEmpty()) {
                //header("Authorization", "Bearer $token")
                addHeader("Authorization", "Bearer $token")
                Log.d("AuthInterceptor", "Token utilizado: Bearer $token")
            }

        }.build()
        return chain.proceed(request)
    }
}