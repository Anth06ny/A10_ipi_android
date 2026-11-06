package com.amonteiro.a10_ipi_android.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun main() {
    var list =  MuseumApiDataSource.loadMuseums()

    for(m in list){
        println("""
            ${m.title} : ${m.artistDisplayName} 
        """.trimIndent())
    }
}

object MuseumApiDataSource {

    private const val API_URL =
        "https://raw.githubusercontent.com/Kotlin/KMP-App-Template/main/list.json"

    //Création et réglage du client
    private val client  = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        //engine { proxy = ProxyBuilder.http("monproxy:1234") }
    }

    suspend fun loadMuseums():List<MuseumDTO>  {
        //requete
        val response = client.get(API_URL)

        if(!response.status.isSuccess()){
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        //parser le json
        //traitement
        return response.body<List<MuseumDTO>>()
    }

}

@Serializable
//DTO
data class MuseumDTO(
    val objectID: Int,
    val title: String,
    val artistDisplayName: String,
    val primaryImage: String
)