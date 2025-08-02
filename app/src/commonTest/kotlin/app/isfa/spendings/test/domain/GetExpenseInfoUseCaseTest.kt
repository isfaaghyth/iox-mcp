package app.isfa.spendings.test.domain

import app.isfa.spendings.robot.GetExpenseInfoUseCaseRobot
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetExpenseInfoUseCaseTest {

    @Test
    fun `returns valid data when gemini successfully extracts receipt`() {
        runBlocking {
            val name = "Coffee Shop"
            val useCase = GetExpenseInfoUseCaseRobot.extractable(name)
            val result = useCase.invoke(null)
            assertTrue { result.isSuccess }
            assertEquals(name, result.getOrNull()?.name)
        }
    }

    @Test
    fun `returns null when gemini fails to parse json from receipt`() {
        runBlocking {
            val useCase = GetExpenseInfoUseCaseRobot.unextractable()
            val result = useCase.invoke(null)
            assertTrue { result.isSuccess }
            assertNull(result.getOrNull())
        }
    }

    @Test
    fun `returns failure when gemini fails to extract information`() {
        runBlocking {
            val useCase = GetExpenseInfoUseCaseRobot.failure()
            val result = useCase.invoke(null)
            assertTrue { result.isFailure }
        }
    }
}
