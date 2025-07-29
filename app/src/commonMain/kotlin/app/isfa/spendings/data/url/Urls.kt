@file:Suppress("UnusedReceiverParameter")

package app.isfa.spendings.data.url

import app.isfa.spendings.util.platform

val NetworkUrl.Gemini.prompt : NetworkUrls
    get() = NetworkUrls {
        UrlModel(
            baseUrl = "generativelanguage.googleapis.com",
            path = "/v1beta/models/gemini-2.0-flash:generateContent",
            params = mapOf(
                "key" to "AIzaSyDsUpcgwaXEuqm_5rgRvdSv7wcRocAUpMA"
            )
        ).createWithHttps()
    }

val NetworkUrl.Expense.list : NetworkUrls
    get() = NetworkUrls {
        UrlModel(
            baseUrl = platform().ipAddress,
            port = 8080,
            path = "/expenses"
        ).create()
    }

val NetworkUrl.Expense.create : NetworkUrls
    get() = NetworkUrls {
        UrlModel(
            baseUrl = platform().ipAddress,
            port = 8080,
            path = "/expense/create"
        ).create()
    }
