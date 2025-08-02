package app.isfa.spendings.robot

import app.isfa.spendings.MainViewModel
import app.isfa.spendings.domain.GetExpenseInfoUseCase
import app.isfa.spendings.domain.GetExpenseListUseCase
import app.isfa.spendings.util.TestCoroutineDispatchers

object MainViewModelRobot {

    fun create(
        expenseInfoRobot: GetExpenseInfoUseCaseRobot = GetExpenseInfoUseCaseRobot,
        expenseListRobot: GetExpenseListUseCaseRobot = GetExpenseListUseCaseRobot,
        expenseInfoUseCase: GetExpenseInfoUseCaseRobot.() -> GetExpenseInfoUseCase = { expenseInfoRobot.extractable("") },
        expenseListUseCase: GetExpenseListUseCaseRobot.() -> GetExpenseListUseCase = { expenseListRobot.empty() },
    ) = MainViewModel(
        expenseInfoUseCase = expenseInfoUseCase(expenseInfoRobot),
        expenseListUseCase = expenseListUseCase(expenseListRobot),
        dispatchers = TestCoroutineDispatchers
    )
}