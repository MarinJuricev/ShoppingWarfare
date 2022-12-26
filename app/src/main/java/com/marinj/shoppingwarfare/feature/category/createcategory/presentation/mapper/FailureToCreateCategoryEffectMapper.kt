package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.mapper

import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewFailure
import javax.inject.Inject

class FailureToCreateCategoryEffectMapper @Inject constructor() {
    // TODO DELETE AND REPLACE WITH Generic Failure to ErrorMessageMapper
    fun map(origin: Failure): CreateCategoryViewEffect {
        return when (origin) {
            is Failure.ErrorMessage -> CreateCategoryViewFailure(origin.errorMessage)
            Failure.Unknown -> CreateCategoryViewFailure("Unknown Error Occurred, please try again later")
        }
    }
}
