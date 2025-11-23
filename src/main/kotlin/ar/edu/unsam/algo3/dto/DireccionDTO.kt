package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Direccion

data class DireccionDTO(
    val direccion : String,
    val calle: String,
    val altura: Int,
    val latitud: Double,
    val longitud: Double,
)

fun Direccion.toDTO() : DireccionDTO =
    DireccionDTO(
        direccion = this.devolverDireccionCompleta(),
        calle = this.calle,
        altura = this.altura,
        latitud = this.ubicacion.x,
        longitud = this.ubicacion.y
    )