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
    var user = UserApiDataSource.loadUser()

    println("""
        Il s'appelle ${user.name} pour le contacter : 
        Phone : ${user.coord?.phone ?: "-"} 
        Mail : ${user.coord?.mail?: "-"} 
    """.trimIndent())

    UserApiDataSource.close()

   UserApiDataSource.loadUser()
}

object UserApiDataSource {

    private const val API_URL = "https://www.amonteiro.fr/api/randomuser"

    //Création et réglage du client
    private val client = HttpClient {
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
    }

    suspend fun loadUser(): UserDTO {
        //Réalise la requete en synchrone et retourne la réponse
        val response = client.get(API_URL)
//teste le code retour de la réponse
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body<UserDTO>()
    }

    fun close() = client.close()



}

//DTO
@Serializable
data class UserDTO(var name:String, var age : Int, var coord:CoordDTO? = null)
@Serializable
data class CoordDTO(var phone : String? = null, var mail:String? = null)
