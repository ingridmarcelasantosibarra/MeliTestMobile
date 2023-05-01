package com.ingridsantos.melitestmobile.domain.entities

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val domainException: DomainException) : Result<T>()
}

inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (domainException: DomainException) -> R
): R = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Failure -> onFailure(domainException)
}


fun <T> Result<T>.getFailure() = when (this) {
    is Result.Failure -> domainException
    else -> null
}

fun <T> Result<T>.getSuccess() = when (this) {
    is Result.Success -> data
    else -> null
}