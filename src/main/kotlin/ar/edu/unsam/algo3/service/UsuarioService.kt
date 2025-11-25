package ar.edu.unsam.algo3.service;

import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.UsuarioCombinadoStrategy
import ar.edu.unsam.algo3.UsuarioFielStrategy
import ar.edu.unsam.algo3.UsuarioImpacienteStrategy
import ar.edu.unsam.algo3.UsuarioMarketingStrategy
import ar.edu.unsam.algo3.UsuarioStrategy
import ar.edu.unsam.algo3.dto.UsuarioDTO
import ar.edu.unsam.algo3.dto.toDomain
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.dto.LocalAPuntuarDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.repositorios.LocalRepositorio

@Service
class UsuarioService(
    private val usuarioRepositorio: UsuarioRepositorio,
    private val localRepositorio: LocalRepositorio,
    private val ingredienteRepositorio: IngredienteRepositorio
) {
    fun getById(id: Int) = usuarioRepositorio.getById(id)

    fun update(id: Int, usuarioDTO: UsuarioDTO): Usuario {
        if (usuarioDTO.id == null) {
            throw ErrorException.BusinessException("El usuario debe poseer un id")
        }
        if (usuarioDTO.id!! != id) {
            throw ErrorException.BusinessException("El id en la URL($id) es distinto del id que viene en el body($usuarioDTO.id)")
        }

        val usuarioExistente = usuarioRepositorio.getById(id)

        // reconstruccion del usuario valido a partir del usuario dto
        val usuarioReconstruido = usuarioDTO.toDomain()

        usuarioExistente.actualizar(usuarioReconstruido)
        usuarioExistente.validar()

        return usuarioRepositorio.update(usuarioExistente)
    }

    fun obtenerLocalesAPuntuar(idUsuario: Int): List<LocalAPuntuarDTO> {
        val usuario = usuarioRepositorio.getById(idUsuario)

        return usuario.localesAPuntuar.map { (local, fechaLimite) ->
            LocalAPuntuarDTO(
                local.toDTO(),
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
            is UsuarioCombinadoStrategy -> {
                val criteriosCopia = criterio.requisitosParticulares.toMutableSet()
                criterio.requisitosParticulares.clear()

                criteriosCopia.forEach { subCriterio ->
                    val criterioValidado = validarYObtenerCriterio(subCriterio, usuario)
                    criterio.agregarUsuarios(criterioValidado)
                }
            }
        }
    }

    private fun validarYObtenerCriterio(criterio: UsuarioStrategy, usuario: Usuario): UsuarioStrategy {
        return when (criterio) {
            is UsuarioFielStrategy -> {
                val nuevoFiel = UsuarioFielStrategy()
                criterio.localesPreferidos.forEach { local ->
                    val localExistente = localRepositorio.getById(local.id!!)
                    nuevoFiel.agregarLocalPreferido(localExistente)
                }
                nuevoFiel
            }
            is UsuarioMarketingStrategy -> {
                if (criterio.textoLlamativo.isEmpty()) {
                    throw ErrorException.BusinessException(
                        "El criterio Marketing debe tener al menos una palabra clave"
                    )
                }
                criterio
            }
            is UsuarioImpacienteStrategy -> {
                if (usuario.distanciaMaximaCercana <= 0) {
                    throw ErrorException.BusinessException(
                        "La distancia máxima debe ser mayor a cero para el criterio Impaciente"
                    )
                }
                criterio
            }
            else -> criterio
        }
    }
}