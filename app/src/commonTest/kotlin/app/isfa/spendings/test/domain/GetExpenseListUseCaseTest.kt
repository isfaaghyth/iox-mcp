package app.isfa.spendings.test.domain

import app.isfa.spendings.robot.GetExpenseListUseCaseRobot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetExpenseListUseCaseTest {

    @Test
    fun `returns list of expenses successfully`() {
        runBlocking {
            val useCase = GetExpenseListUseCaseRobot.expenses()
            val result = useCase.fetch().first()

            assertTrue { result.isSuccess }
            assertNotNull(result.getOrNull())
        }
    }

    @Test
    fun `returns empty list successfully when no expenses exist`() {
        runBlocking {
            val useCase = GetExpenseListUseCaseRobot.empty()
            val result = useCase.fetch().first()

            assertTrue { result.isSuccess }
            assertNotNull(result.getOrNull())
            assertEquals(0, result.getOrNull()?.size)
        }
    }
}
