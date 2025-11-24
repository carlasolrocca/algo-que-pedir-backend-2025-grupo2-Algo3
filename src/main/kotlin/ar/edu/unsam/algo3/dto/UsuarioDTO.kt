package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.UsuarioStrategy
import ar.edu.unsam.algo3.dto.DireccionDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.toDomain

data class UsuarioDTO (
    var id: Int,
    var nombre: String,
    var apellido: String,
    var mail: String,
    var direccion: DireccionDTO,
    var distanciaMaximaCercana: Double,
    var criterio: CriterioDTO,
    var ingredientesPreferidos: MutableSet<IngredienteUsuarioDTO>,
    var ingredientesEvitar: MutableSet<IngredienteUsuarioDTO>
)

fun Usuario.toDTO() = UsuarioDTO(
    id=id!!,
    nombre=nombre,
    apellido=apellido,
    mail=mail,
    direccion=direccion.toDTO(),
    distanciaMaximaCercana=distanciaMaximaCercana,
    criterio=tipoDeUsuario.toCriterioDTO(),
    ingredientesPreferidos=this.ingredientesPreferidos.map { it.toUsuarioDTO() }.toMutableSet(),
    ingredientesEvitar=this.ingredientesProhibidos.map { it.toUsuarioDTO() }.toMutableSet()
)

fun UsuarioDTO.toDomain() = Usuario(
    nombre=this.nombre,
    apellido=this.apellido,
    mail=this.mail,
    direccion=this.direccion.toDomain(),
    distanciaMaximaCercana = this.distanciaMaximaCercana
).apply {
    this.id = this@toDomain.id
}