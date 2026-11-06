package com.amonteiro.a10_ipi_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a10_ipi_android.data.fake.WeatherFakeDataSource
import com.amonteiro.a10_ipi_android.data.remote.WeatherApiDataSource
import com.amonteiro.a10_ipi_android.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("aa")

    while (viewModel.runInProgress.value) {
        println("Attente...")
        delay(500.milliseconds)
    }

    //Affichage de la liste (qui doit être remplie) contenue dans la donnée observable
    println("List : ${viewModel.dataList.value}")
    println("ErrorMessage : ${viewModel.errorMessage.value}" )


    //Pour que le programme s'arrête, inutile sur Android
    WeatherApiDataSource.close()
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<Weather>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    init {
        dataList.value= WeatherFakeDataSource.loadFakeData()
    }

    fun loadWeathers(cityName: String) {

        runInProgress.value = true
        errorMessage.value = ""


        viewModelScope.launch(Dispatchers.IO) {
            try {

                if(cityName.length < 3){
                    throw Exception("Il faut au moins 3 caractères")
                }
                dataList.value = WeatherApiDataSource.loadWeathers(cityName)
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur est survenue"
            }
            finally {
                runInProgress.value = false
            }
        }


    }
}