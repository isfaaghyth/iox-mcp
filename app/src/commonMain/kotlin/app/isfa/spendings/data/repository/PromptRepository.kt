package app.isfa.spendings.data.repository

import app.isfa.spendings.data.repository.prompt.PromptAsset
import spendings.app.generated.resources.Res

interface PromptRepository {

    suspend fun prompt(file: PromptAsset): String
}

class PromptRepositoryImpl : PromptRepository {

    override suspend fun prompt(file: PromptAsset): String {
        return Res.readBytes("files/${file.name()}").decodeToString()
    }
}