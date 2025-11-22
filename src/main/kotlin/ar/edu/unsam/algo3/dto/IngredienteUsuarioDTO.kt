package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Ingrediente

data class IngredienteUsuarioDTO(
    val id: Int,
    val nombre: String
)

fun Ingrediente.toUsuarioDTO() = IngredienteUsuarioDTO(
    id = this.id!!,
    nombre = this.nombre
)