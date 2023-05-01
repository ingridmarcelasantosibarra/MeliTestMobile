package com.ingridsantos.melitestmobile.domain.repository

import com.ingridsantos.melitestmobile.domain.entities.DomainException

interface DomainExceptionRepository {
    fun manageError(error: Throwable): DomainException
}
