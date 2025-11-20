package ar.edu.unsam.algo3.service;

import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.UsuarioFielStrategy
import ar.edu.unsam.algo3.UsuarioImpacienteStrategy
import ar.edu.unsam.algo3.UsuarioMarketingStrategy
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
    fun getById(id: Int) = usuarioRepositorio.getById(id)

    fun update(id: Int, usuarioActualizado: Usuario): Usuario {
        if (usuarioActualizado.id == null) {
            throw ErrorException.BusinessException("El usuario debe poseer un id")
        }
        if (usuarioActualizado.id!! != id) {
            throw ErrorException.BusinessException("El id en la URL($id) es distinto del id que viene en el body($usuarioActualizado.id)")
        }

        val usuarioExistente = usuarioRepositorio.getById(id)

        // Asignacion del criterio
        asignarCriterio(usuarioActualizado)

        // Asignacion de los ingredientes preferidos y los que se evitan
        asignarIngredientes(
            usuarioActualizado,
            usuarioActualizado.ingredientesPreferidos,
            "preferidos",
            agregar = { usuario, ingrediente -> usuario.agregarPreferido(ingrediente) })
        asignarIngredientes(
            usuarioActualizado,
            usuarioActualizado.ingredientesProhibidos,
            "prohibidos",
            agregar = { usuario, ingrediente -> usuario.agregarProhibido(ingrediente) })

        usuarioExistente.actualizar(usuarioActualizado)
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

    // Metodos internos para la asignacion de los ingredientes y el criterio.
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

    private fun asignarCriterio(usuario: Usuario){
        when (val criterio = usuario.tipoDeUsuario) {
            is UsuarioFielStrategy -> {
                // validar y obtener el local del repositorio
                val localesCopia = criterio.localesPreferidos.toMutableSet()
                criterio.localesPreferidos.clear()

                localesCopia.forEach { local ->
                    val localExistente = localRepositorio.getById(local.id!!)
                    criterio.agregarLocalPreferido(localExistente)
                }
            }

            is UsuarioMarketingStrategy -> {
                // Valida que efectivamente tenga alguna palabra clave agregada
                if (criterio.textoLlamativo.isEmpty()) {
                    throw ErrorException.BusinessException("El criterio Marketing debe tener al menos una palabra clave")
                }
            }

            is UsuarioImpacienteStrategy -> {
                if (usuario.distanciaMaximaCercana <= 0) {
                    throw ErrorException.BusinessException("La distancia debe ser mayor a cero")
                }
            }
        }
    }
}