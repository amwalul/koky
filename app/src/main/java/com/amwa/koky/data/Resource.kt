package com.amwa.koky.data

sealed class Resource<out T>(val data: T? = null, val message: String? = null)
class Success<out T>(data: T) : Resource<T>(data)
object Loading : Resource<Nothing>()
class Error(message: String) : Resource<Nothing>(message = message)