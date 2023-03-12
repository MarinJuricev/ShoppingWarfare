package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category

interface UndoCategoryDeletion {

    suspend operator fun invoke(category: Category): Either<Failure, Unit>
}
