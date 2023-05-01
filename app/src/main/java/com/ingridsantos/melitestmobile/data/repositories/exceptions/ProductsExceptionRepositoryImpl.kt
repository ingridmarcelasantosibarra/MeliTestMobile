package com.ingridsantos.melitestmobile.data.repositories.exceptions

import com.ingridsantos.melitestmobile.domain.entities.DomainException
import com.ingridsantos.melitestmobile.domain.exceptions.CommonErrors
import com.ingridsantos.melitestmobile.domain.repository.DomainExceptionRepository

class ProductsExceptionRepositoryImpl : CommonErrors(), DomainExceptionRepository {
    override fun manageError(error: Throwable): DomainException {
        return manageException(error)
    }
}

