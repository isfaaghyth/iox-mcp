@file:OptIn(ExperimentalCoroutinesApi::class)

package app.isfa.spendings.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

open class DispatcherMainTest {

    protected fun test(invoke: (TestScope) -> Unit) = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        invoke(this)
    }
}