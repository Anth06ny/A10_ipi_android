package com.amonteiro.a10_ipi_android.exo


fun main() {
    var t = ThermometerEntity(0, 100, 10)
    var t2 = ThermometerEntity(0, 100, 10)

    println(t.value)
    t.value = 150

    println(t.value)

    val car = CarEntity(marque =  "toto" , "bla")
    car.marque = "fvgvbhnj,;"


}



class RandomName() {
    private val list = arrayListOf("Toto", "Bob", "Titi")
    private var oldValue = ""

    fun add(name: String) {
        if (name.isNotBlank() && name !in list)
            list.add(name)


    }

    fun next() = list.random()

    fun addAll(vararg names : String){
        for(name in names){
            add(name)
        }
    }

    fun nextDiff(): String {
        var newValue = next()
        while(newValue  == oldValue){
            newValue = next()
        }

        oldValue = newValue
        return newValue
    }


    fun nextDiffV2(): String {
        oldValue = list.filter { it != oldValue }.random()

        return oldValue
    }

    fun nextDiffV3() = list.filter { it != oldValue }.random().also { oldValue = it }

    fun next2() = Pair(nextDiff(), nextDiff())

}


class ThermometerEntity(val min: Int, val max: Int, value: Int) {
    var value: Int = value
        set(newValue) {
            if (newValue > max) {
                field = max
            }
            else if (newValue < min) {
                field = min
            }
            else {
                field = newValue
            }
        }
}

data class CarEntity(var marque: String, var model: String) {
    var color = ""

}