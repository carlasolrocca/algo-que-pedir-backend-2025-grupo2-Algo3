package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.MedioDePago

data class LocalDTO(
    val nombre: String,
    val urlImagenLocal: String,
    val direccion: String,
    val altura: Int,
    val latitud: Double,
    val longitud: Double,
    val porcentajeSobreCadaPlato: Double,
    val porcentajeRegaliasDeAutor: Double,
    val mediosDePago: Set<MedioDePago>
)