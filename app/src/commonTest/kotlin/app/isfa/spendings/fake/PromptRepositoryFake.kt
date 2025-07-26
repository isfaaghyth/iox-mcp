package app.isfa.spendings.fake

import app.isfa.spendings.data.repository.PromptRepository
import app.isfa.spendings.data.repository.prompt.PromptAsset

class PromptRepositoryFake : PromptRepository {

    override suspend fun read(file: PromptAsset) = ""
}