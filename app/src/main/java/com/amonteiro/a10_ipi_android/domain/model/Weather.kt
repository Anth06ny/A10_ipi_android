package com.amonteiro.a10_ipi_android.domain.model

data class Weather(
    var id: Int, //id d'un point météo
    var name: String,
    var temp: Double, //Température
    var speed: Double, //Vitesse du vent
    var description: String, //1er description
    var icon: String //1er icone
) {

    fun getResume() = """
        Il fait $temp° à $name (id=$id) avec un vent de 3.6 m/s
        -Description : $description
        -Icône : $icon
    """.trimIndent()

}