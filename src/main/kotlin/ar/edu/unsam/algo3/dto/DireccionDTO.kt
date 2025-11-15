package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Direccion

data class DireccionDTO(
    val direccion : String,
    val latitud: Double,
    val longitud: Double,
)

fun Direccion.toDTO() : DireccionDTO =
    DireccionDTO(
        direccion = this.devolverDireccionCompleta(),
        latitud = this.ubicacion.x,
        longitud = this.ubicacion.y
    )