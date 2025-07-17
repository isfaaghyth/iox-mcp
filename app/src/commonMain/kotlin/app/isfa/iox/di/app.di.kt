package app.isfa.iox.di

import app.isfa.iox.data.api.GeminiApi
import app.isfa.iox.data.repository.GeminiRepository
import app.isfa.iox.data.repository.GeminiRepositoryImpl
import app.isfa.iox.domain.GetGeminiUseCase

private fun createGeminiApi(): GeminiApi = GeminiApi.create()
private fun createGeminiRepository(): GeminiRepository = GeminiRepositoryImpl(createGeminiApi())
private fun createGeminiUseCase(): GetGeminiUseCase = GetGeminiUseCase(createGeminiRepository())