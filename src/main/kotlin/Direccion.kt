package ar.edu.unsam.algo2

import org.uqbar.geodds.Point

class Direccion(
    val calle: String = "",
    val altura: Int = 0,
    val ubicacion: Point = Point(0, 0)){

    fun distanciaCon(otraDireccion: Direccion): Double {
        return ubicacion.distance(otraDireccion.ubicacion)
    }
}
