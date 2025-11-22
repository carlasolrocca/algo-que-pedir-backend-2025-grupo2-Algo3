package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.MedioDePago

data class LocalClienteDTO (
    val idLocal: Int,
    val nombre: String,
    val urlImagenLocal: String,
    var mediosDePago: MutableSet<MedioDePago>,
    val rating: Double,
    val reviews: String,
    val tarifaEntrega: Double,
    val recargosMedioDePago: Map<String, Double>
)

fun Local.toClienteDTO() = LocalClienteDTO (
    idLocal = this.id!!,
    nombre = this.nombre,
    mediosDePago = this.mediosDePago,
    urlImagenLocal = this.urlImagenLocal,
    rating = this.calcularPromedioPuntuacion(),
    reviews = "Muy bueno!", //El local no tiene aun algo para recibir las reviews, deberia ser una lista que almacene strings?
    tarifaEntrega = this.tarifaEntrega,
    recargosMedioDePago = this.recargosMedioDePago.mapKeys { it.key.name }
)

fun LocalClienteDTO.toDomain(): Local {
    return Local(
        nombre = this.nombre,
        mediosDePago =  this.mediosDePago,
        urlImagenLocal = this.urlImagenLocal
    ).apply {
        this.id = this@toDomain.idLocal
    }
}