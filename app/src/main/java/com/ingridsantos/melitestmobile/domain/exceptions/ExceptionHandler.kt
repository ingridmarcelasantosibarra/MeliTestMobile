package com.ingridsantos.melitestmobile.domain.exceptions

import com.ingridsantos.melitestmobile.R
import com.ingridsantos.melitestmobile.domain.entities.DomainException
import com.ingridsantos.melitestmobile.domain.entities.InternalErrorException
import com.ingridsantos.melitestmobile.domain.entities.NoConnectivityDomainException
import com.ingridsantos.melitestmobile.domain.entities.ParseException
import com.ingridsantos.melitestmobile.domain.entities.TimeOutException

/**
 * Created by Camilo Medina on 11/11/2021
 */
class ExceptionHandler {

    fun manageException(domainException: DomainException): Int =
        when (domainException) {
            is TimeOutException -> R.string.error_time_out
            is InternalErrorException -> R.string.error_internal_error_exception
            is ParseException -> R.string.error_parsing_error
            is NoConnectivityDomainException -> R.string.error_internet_connection
            else -> R.string.error_some_wrong
        }
}
