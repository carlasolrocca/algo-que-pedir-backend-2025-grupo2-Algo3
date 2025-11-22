package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Local

data class LocalCriterioDTO(
    val id: Int,
    val nombre: String,
    val imagen: String,
    val puntuacion: Double
)

fun Local.toCriterioDTO() = LocalCriterioDTO(
    id = this.id!!,
    nombre = this.nombre,
    imagen = this.urlImagenLocal,
    puntuacion = this.calcularPromedioPuntuacion()
)