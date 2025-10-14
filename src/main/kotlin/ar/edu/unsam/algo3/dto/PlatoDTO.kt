package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Plato

data class PlatoDTO(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var valorBase: Double,
    var esDeAutor: Boolean,
    var estaEnPromocion: Boolean,
    var valorPorcentaje: Double,
    var costoProduccion: Double,
    var ingredientes: MutableSet<IngredienteDTO>
)

fun Plato.toDTO() = PlatoDTO(
    id=id!!,
    nombre=nombre,
    descripcion=descripcion,
    valorBase=valorBase,
    esDeAutor=esdeAutor,
    estaEnPromocion= !this.esNuevo(),
    valorPorcentaje=porcentajeDescuento,
    costoProduccion= this.costoDeProduccion(),
    ingredientes= this.listaDeIngredientes.map { it.toDTO() }.toMutableSet()
)
