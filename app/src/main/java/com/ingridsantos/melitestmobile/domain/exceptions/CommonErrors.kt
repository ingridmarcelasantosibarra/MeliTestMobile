package com.ingridsantos.melitestmobile.domain.exceptions

import com.google.gson.JsonSyntaxException
import com.ingridsantos.melitestmobile.domain.entities.DomainException
import com.ingridsantos.melitestmobile.domain.entities.InternalErrorException
import com.ingridsantos.melitestmobile.domain.entities.NoConnectivityDomainException
import com.ingridsantos.melitestmobile.domain.entities.NoConnectivityException
import com.ingridsantos.melitestmobile.domain.entities.ParseException
import com.ingridsantos.melitestmobile.domain.entities.TimeOutException
import com.ingridsantos.melitestmobile.domain.entities.UnknownError
import com.squareup.moshi.JsonDataException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class CommonErrors {

    fun manageException(throwable: Throwable): DomainException {
        return manageJavaErrors(throwable)
    }

    fun manageJavaErrors(throwable: Throwable): DomainException {
        return when (throwable) {
            is SocketTimeoutException -> TimeOutException
            is ConnectException -> InternalErrorException(throwable.message ?: String())
            else -> manageParsingExceptions(throwable)
        }
    }

    fun manageParsingExceptions(throwable: Throwable): DomainException {
        return when (throwable) {
            is JsonDataException -> ParseException
            is JsonSyntaxException -> ParseException
            else -> manageOtherException(throwable)
        }
    }

    fun manageOtherException(throwable: Throwable): DomainException {
        return when (throwable) {
            is NoConnectivityException -> NoConnectivityDomainException
            else -> UnknownError(throwable.message.toString())
        }
    }
}
