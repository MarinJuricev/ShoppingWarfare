package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface DeleteCategory {

    suspend operator fun invoke(categoryId: String): Either<Failure, Unit>
}
