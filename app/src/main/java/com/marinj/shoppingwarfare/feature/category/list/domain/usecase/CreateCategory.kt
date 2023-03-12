package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface CreateCategory {

    suspend operator fun invoke(
        title: String?,
        backgroundColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit>
}
