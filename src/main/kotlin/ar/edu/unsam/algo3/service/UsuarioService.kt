package ar.edu.unsam.algo3.service;

import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.dto.LocalAPuntuarDTO
import ar.edu.unsam.algo3.dto.toDto
import ar.edu.unsam.algo3.repositorios.LocalRepositorio

@Service
class UsuarioService(
    private val usuarioRepositorio: UsuarioRepositorio,
    private val localRepositorio: LocalRepositorio,
    private val ingredienteRepositorio: IngredienteRepositorio
) {
    fun getById(id: Int) = usuarioRepositorio.getById((id))

    fun update(id: Int, actualizarUsuario: Usuario): Usuario {
        if (actualizarUsuario.id == null) {
            throw ErrorException.BusinessException("El usuario debe poseer un id")
        }
        if (actualizarUsuario.id!! != id) {
            throw ErrorException.BusinessException("El id en la URL es distinto del id que viene en el body")
        }

        val usuarioExistente = usuarioRepositorio.getById(id)
        asignarPreferencias()
        asignarIngredientes(
            actualizarUsuario,
            actualizarUsuario.ingredientesPreferidos,
            "preferidos",
            agregar = { usuario, ingrediente -> usuario.agregarPreferido(ingrediente) })
        asignarIngredientes(
            actualizarUsuario,
            actualizarUsuario.ingredientesProhibidos,
            "prohibidos",
            agregar = { usuario, ingrediente -> usuario.agregarProhibido(ingrediente) })

        usuarioExistente.actualizar(actualizarUsuario)
        usuarioExistente.validar()

        return usuarioRepositorio.update(usuarioExistente)
    }

    fun obtenerLocalesAPuntuar(idUsuario: Int): List<LocalAPuntuarDTO> {
        val usuario = usuarioRepositorio.getById(idUsuario)

        return usuario.localesAPuntuar.map { (local, fechaLimite) ->
            LocalAPuntuarDTO(
                local.toDto(),
                fechaLimite = fechaLimite.toString()
            )
        }
    }

    fun puntuarLocal(idUsuario: Int, idLocal: Int, puntuacion: Double) {
        val usuario = usuarioRepositorio.getById(idUsuario)
        val local = localRepositorio.getById(idLocal)

        // Llama al metodo de Usuario que valida y puntúa
        usuario.puntuarLocal(local, puntuacion)
    }

    private fun asignarIngredientes(
        usuario: Usuario,
        ingredientesActuales: MutableSet<Ingrediente>,
        tipoIngrediente: String,
        agregar: (Usuario, Ingrediente) -> Unit
    ) {
        val ingredientesCopia = ingredientesActuales.toMutableSet()
        ingredientesActuales.clear()

        val ingredientesFaltantes = mutableSetOf<String>()
        ingredientesCopia.forEach { ing ->
            val ingredienteExistente = ingredienteRepositorio.getById(ing.id!!)

            if (ingredienteExistente != null) {
                agregar(usuario, ingredienteExistente)
            } else {
                ingredientesFaltantes.add(ing.nombre)
            }
        }

        if (ingredientesFaltantes.isNotEmpty()) {
            throw ErrorException.BusinessException("No se encontraron los ingredientes: $tipoIngrediente: ${ingredientesFaltantes.joinToString()}")
        }


    }
}