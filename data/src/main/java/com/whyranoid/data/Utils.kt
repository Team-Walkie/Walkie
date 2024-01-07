package com.whyranoid.data

import retrofit2.Response

fun <T, R> Response<T>.getResult(transform: (T) -> R): R {
    if (this.isSuccessful.not())
        throw Exception(this.errorBody().toString())
    else if (this.body() == null)
        throw Exception(this.message())

    return requireNotNull(this.body()?.let(transform) ?: throw Exception("empty response"))
}