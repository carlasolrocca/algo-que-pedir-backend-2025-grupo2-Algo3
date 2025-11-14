package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Local

data class LocalClienteDTO (
    val idLocal: Int,
    val nombre: String,
    val urlImagenLocal: String,
    val rating: Double,
    val reviews: String
)

fun Local.toClienteDTO() = LocalClienteDTO (
    idLocal = this.id!!,
    nombre = this.nombre,
    urlImagenLocal = this.urlImagenLocal,
    rating = this.calcularPromedioPuntuacion(),
    reviews = "Muy bueno!" //El local no tiene aun algo para recibir las reviews, deberia ser una lista que almacene strings?
)