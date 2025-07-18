package app.isfa.iox.data.repository

import app.isfa.iox.data.repository.prompt.PromptAsset
import ioxmcp.app.generated.resources.Res

interface PromptRepository {

    suspend fun prompt(file: PromptAsset): String
}

class PromptRepositoryImpl : PromptRepository {

    override suspend fun prompt(file: PromptAsset): String {
        return Res.readBytes("files/${file.name()}").decodeToString()
    }
}