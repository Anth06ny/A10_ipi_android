package com.amonteiro.a10_ipi_android.exo

fun main() {

    var v1 = "aaa"
    var v2: String? = "aaa"
    var v3: String? = null

    println(v1.uppercase())
    println(v2?.uppercase())
    println(v3?.uppercase())

    var v4 = "nullnull"

    var res = boulangerie()
    res = boulangerie(1, 2, 3)
    res = boulangerie(nbSand = 3)
}

fun boulangerie(nbCroi: Int = 0, nbBag: Int = 0, nbSand: Int = 0)
    = nbCroi * PRICE_CROISSANT + nbBag * PRICE_BAGUETTE + nbSand * PRICE_SANDWITCH
