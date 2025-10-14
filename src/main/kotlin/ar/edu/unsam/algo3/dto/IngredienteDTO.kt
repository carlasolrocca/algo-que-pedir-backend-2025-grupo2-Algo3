package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente

data class IngredienteDTO(
    var id: Int,
    var nombre: String,
    var costo: Double,
    var grupo: EnumGrupoAlimenticio,
    var origenAnimal: Boolean
)

fun Ingrediente.toDTO() = IngredienteDTO(
    id=id!!,
    nombre=nombre,
    costo=costoMercado,
    grupo=grupoAlimenticio,
    origenAnimal=origenAnimal
)