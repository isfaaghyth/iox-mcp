package app.isfa.spendings.util

import kotlinx.coroutines.Dispatchers

object TestCoroutineDispatchers : CoroutineDispatchers {
    override val main = Dispatchers.Main
    override val io = Dispatchers.Main
    override val default = Dispatchers.Main
}