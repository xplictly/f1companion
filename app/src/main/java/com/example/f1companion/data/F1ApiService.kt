package com.example.f1companion.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface F1ApiService {
    suspend fun getSeasons(): List<Season>
    suspend fun getCircuits(): List<Circuit>
}

// This is a mock implementation that returns our hardcoded data.
// When you have a real backend, you will replace this with a Ktor implementation.
class MockF1ApiService : F1ApiService {
    override suspend fun getSeasons(): List<Season> {
        return MockData.seasons
    }

    override suspend fun getCircuits(): List<Circuit> {
        return MockData.circuits.values.toList()
    }
}

// This is where you would configure the real Ktor client
object KtorF1ApiService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    // In a real app, you would have functions like this:
    /*
    suspend fun getSeasons(): List<Season> {
        return client.get("https://your-f1-api.com/seasons").body()
    }
    */
}