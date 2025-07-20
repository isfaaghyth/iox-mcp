package app.isfa.spendings.data.repository

import app.isfa.spendings.data.repository.prompt.PromptAsset
import spendings.app.generated.resources.Res

interface PromptRepository {

    suspend fun read(file: PromptAsset): String
}

class PromptRepositoryImpl : PromptRepository {

    override suspend fun read(file: PromptAsset): String {
        return runCatching { Res.readBytes("files/${file.name()}").decodeToString() }
            .getOrNull()
            .orEmpty()
    }
}