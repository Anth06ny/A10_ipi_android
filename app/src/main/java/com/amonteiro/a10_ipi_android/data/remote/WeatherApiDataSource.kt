package com.amonteiro.a10_ipi_android.data.remote

import com.amonteiro.a10_ipi_android.domain.model.Weather
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
    val list = WeatherApiDataSource.loadWeathers("Nice")

    for (w in list) {
        println(w.getResume())
    }
}

object WeatherApiDataSource {

    private const val API_URL = "https://api.openweathermap.org/data/2.5/find?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q="

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

    //Retoure la liste des points météos de la ville en paramètre
    suspend fun loadWeathers(cityName: String): List<Weather> {
        //Requête avec ktor
        var response = client.get(API_URL + cityName)
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        //Récupérer les DTO
       return  response.body<WeatherAPIResultDTO>().list.map { dto->
            val icon  =  dto.weather.firstOrNull()?.icon ?: ""
            Weather(
                id = dto.id,
                name = dto.name,
                temp = dto.main.temp,
                speed = dto.wind.speed,
                description = dto.weather.getOrNull(0)?.description ?: "-",
                icon =  "https://openweathermap.org/img/wn/$icon@4x.png"
            )
        }
    }

    fun close() = client.close()


}

//DTO
@kotlinx.serialization.Serializable
data class WeatherAPIResultDTO(var list: List<WeatherDTO>)

@kotlinx.serialization.Serializable
data class WeatherDTO(var id: Int, var name: String, var main: TempDTO, var wind: WindDTO, var weather: List<DescriptionDTO>)

@Serializable
data class TempDTO(var temp: Double)

@Serializable
data class WindDTO(val speed: Double)

@Serializable
data class DescriptionDTO(var description: String, var icon: String)

