package com.marinj.shoppingwarfare.feature.createcategory.presentation.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect.CreateCategoryFailure
import javax.inject.Inject

class FailureToCreateCategoryEffectMapper @Inject constructor() : Mapper<CreateCategoryEffect, Failure> {

    override suspend fun map(origin: Failure): CreateCategoryEffect {
        return when (origin) {
            is Failure.ErrorMessage -> CreateCategoryFailure(origin.errorMessage)
            Failure.Unknown -> CreateCategoryFailure("Unknown Error Occurred, please try again later")
        }
    }
}