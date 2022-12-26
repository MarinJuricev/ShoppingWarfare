package com.marinj.shoppingwarfare.core.mapper

import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FailureToStringMapper @Inject constructor() {

    fun map(origin: Failure): String = when (origin) {
        is ErrorMessage -> origin.errorMessage
        Unknown -> "Unknown Error Occurred, please try again later"
    }
}
