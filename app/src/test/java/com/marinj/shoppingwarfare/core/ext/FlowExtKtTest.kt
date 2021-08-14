package com.marinj.shoppingwarfare.core.ext

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class FlowExtKtTest {
    @Test
    fun `safeUpdate should update existing flow with new value`() = runBlockingTest {
        val currentFlow = MutableStateFlow(1)
        val updatedValue = 4

        currentFlow.safeUpdate(updatedValue)

        currentFlow.test {
            assertThat(awaitItem()).isEqualTo(updatedValue)
        }
    }
}