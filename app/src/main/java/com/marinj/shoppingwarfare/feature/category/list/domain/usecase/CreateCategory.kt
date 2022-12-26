package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface CreateCategory {

    suspend operator fun invoke(
        title: String?,
        backgroundColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit>
}
