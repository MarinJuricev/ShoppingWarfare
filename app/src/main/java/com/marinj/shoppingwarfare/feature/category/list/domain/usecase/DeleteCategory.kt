package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface DeleteCategory {

    suspend operator fun invoke(categoryId: String): Either<Failure, Unit>
}
