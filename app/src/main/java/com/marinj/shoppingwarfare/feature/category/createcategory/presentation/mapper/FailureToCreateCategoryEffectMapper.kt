package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.mapper

import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEffect
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEffect.CreateCategoryFailure
import javax.inject.Inject

class FailureToCreateCategoryEffectMapper @Inject constructor() {

    fun map(origin: Failure): CreateCategoryEffect {
        return when (origin) {
            is Failure.ErrorMessage -> CreateCategoryFailure(origin.errorMessage)
            Failure.Unknown -> CreateCategoryFailure("Unknown Error Occurred, please try again later")
        }
    }
}
