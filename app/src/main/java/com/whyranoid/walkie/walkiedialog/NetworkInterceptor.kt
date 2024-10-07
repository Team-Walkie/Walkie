package com.whyranoid.walkie.walkiedialog

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(
    private val onRequest: () -> Unit,
    private val onResponse: (isSuccessFul: Boolean) -> Unit,
    private val excludedUrls: List<Regex>,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("ju0828", request.url.toString())

        Log.d("ju0828", "${request.url} validate = ${excludedUrls.all { request.url.toString().contains(it).not() }}")

        // 요청 URL이 제외할 URL과 같지 않으면 콜백 호출
        if (excludedUrls.all { request.url.toString().contains(it).not() }) {
            onRequest() // 요청 전 콜백 호출
            Log.d("ju0828", "${request.url} request called")

        }

        return try {
            val response = chain.proceed(request)

            // 응답 후 콜백 호출
            if (excludedUrls.all { request.url.toString().contains(it).not() }) {
                onResponse(response.isSuccessful) // 응답 후 콜백 호출
            }
            response
        } catch (e: Exception) {
            onResponse(false)
            throw e
        }
    }
}