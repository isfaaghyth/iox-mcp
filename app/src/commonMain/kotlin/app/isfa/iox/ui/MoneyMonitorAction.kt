package app.isfa.iox.ui

import app.isfa.iox.intent.ImageIntentData

sealed interface MoneyMonitorEvent
sealed interface MoneyMonitorEffect

data class SendRequest(val model: ImageIntentData) : MoneyMonitorEvent