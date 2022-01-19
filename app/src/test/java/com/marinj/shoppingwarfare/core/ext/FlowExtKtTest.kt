package com.marinj.shoppingwarfare.core.ext

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class FlowExtKtTest {
    @Test
    fun `safeUpdate should update existing flow with new value`() = runTest {
        val currentFlow = MutableStateFlow(1)
        val updatedValue = 4

        currentFlow.safeUpdate(updatedValue)

        currentFlow.test {
            assertThat(awaitItem()).isEqualTo(updatedValue)
        }
    }
}
