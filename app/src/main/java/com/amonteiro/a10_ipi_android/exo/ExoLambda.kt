package com.amonteiro.a10_ipi_android.exo

import com.amonteiro.a10_ipi_android.data.remote.WindDTO


class MyLiveData(value : WindDTO) {
    var value = value
        set(newValue) {
            field = newValue
            action(newValue)
        }

    var action : (WindDTO)->Unit = {}

}

fun main() {
    //exo1()
    var toto = MyLiveData(WindDTO(5.0))

    toto.action = { println(it)}

    toto.value = toto.value.copy(toto.value.speed + 1)





}





data class PersonEntity(var name:String, var note:Int)

fun exo3(){
    val list = arrayListOf(PersonEntity ("toto", 16),
        PersonEntity ("Tata", 20),
        PersonEntity ("Toto", 8),
        PersonEntity ("Charles", 14))

    println("Afficher la sous liste de personne ayant 10 et +")
    //println(list.filter { it.note >=10 })
    //Pour un affichage de notre choix
    println(list.filter { it.note >=10 }.joinToString("\n") { "-${it.name} : ${it.note}"})

    val isToto: (PersonEntity) -> Boolean = {it.name.equals("toto", true) }

    //TODO
    println("\n\nAfficher combien il y a de Toto dans la classe ?")
    println(list.count {it.name.equals("toto", true) })
    println(list.count (isToto))


    println("\n\nAfficher combien de Toto ayant la moyenne (10 et +)")
    println("\n\nAfficher combien de Toto ont plus que la moyenne de la classe")
    var average = list.map { it.note }.average()
    val res = list.filter { isToto(it) && it.note > average }

    println("\n\nAfficher la list triée par nom sans doublon")
    println("\n\nAjouter un point a ceux n’ayant pas la moyenne (<10)")
    println("\n\nAjouter un point à tous les Toto")


    println("\n\nRetirer de la liste ceux ayant la note la plus petite. (Il peut y en avoir plusieurs)")
    var min = list.minOf { it.note }
    list.removeIf { it.note == min }


    println("\n\nAfficher les noms de ceux ayant la moyenne(10et+) par ordre alphabétique")

    //TODO Créer une variable isToto contenant une lambda qui teste si un PersonEntity s'appelle Toto

    println("\n\nDupliquer la liste ainsi que tous les utilisateurs (nouvelle instance) qu'elle contient")
    println("\n\nAfficher par notes croissantes les élèves ayant eu cette note comme sur l'exemple")
}


fun exo1(){
    //Déclaration
    val hour  : (Int)->Int = {it : Int -> it/60}
    val hour2   = {it : Int -> it/60}
    val hour3  : (Int)->Int = {it  -> it/60}
    val hour4  : (Int)->Int = { it/60}

    println(hour(123))

    var minToMinHour :((Int?) -> Pair<Int,Int>?)? = { if(it != null) Pair(it/60, it%60) else null }

    println(minToMinHour?.invoke(123))

    minToMinHour = null


}