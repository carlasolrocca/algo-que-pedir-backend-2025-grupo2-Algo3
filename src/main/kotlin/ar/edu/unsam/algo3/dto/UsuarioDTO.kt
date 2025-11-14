package ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.UsuarioStrategy
import ar.edu.unsam.algo3.dto.DireccionDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDTO

data class UsuarioDTO (
    var id: Int,
    var nombre: String,
    var apellido: String,
    var mail: String,
    var direccion: DireccionDTO,
    var criterio: TipoCriterioDTO,
    var ingredientesPreferidos: MutableSet<IngredienteDTO>,
    var ingredientesEvitar: MutableSet<IngredienteDTO>
)

fun Usuario.toDTO() = UsuarioDTO(
    id=id!!,
    nombre=nombre,
    apellido=apellido,
    mail=mail,
    direccion=direccion.toDTO(),
    criterio=tipoDeUsuario.toTipoCriterioDTO(),
    ingredientesPreferidos=this.ingredientesPreferidos.map { it.toDTO() }.toMutableSet(),
    ingredientesEvitar=this.ingredientesProhibidos.map { it.toDTO() }.toMutableSet()
)