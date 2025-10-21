package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.LocalRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import org.springframework.stereotype.Service

@Service
class PlatoService (
    private val platoRepository: PlatoRepositorio,
    private val localRepository: LocalRepositorio,
    private val ingredienteRepository: IngredienteRepositorio
) {
    fun getAll() = platoRepository.findAll()

    fun getById(id: Int) = platoRepository.getById(id)

    fun create(nuevoPlato: Plato): Plato {
        if (nuevoPlato.id != null) {
            throw ErrorException.BusinessException("No se debe pasar el identificador del plato")
        }
        asignarLocal(nuevoPlato)
        asignarIngredientes(nuevoPlato)
        nuevoPlato.validar()
        return platoRepository.create(nuevoPlato)
    }

    fun update(id: Int, actualizarPlato: Plato): Plato {
        // Primero valida si id es null, dodino también lo hace en tareas de la misma manera
        if (actualizarPlato.id == null){
            throw ErrorException.BusinessException("El objeto debe tener un ID")
        }
        // Validacion extra de URL (como en tareas)
        if (actualizarPlato.id!! !== id) {
            throw ErrorException.BusinessException("Id en URL distinto del id que viene en el body")
        }

        val platoExistente = platoRepository.getById(id) // se recupera el plato actual del repo

        // valida si se actualizan ingredientes, agregarlos y usar el repo de ingredientes
        asignarIngredientes(actualizarPlato)

        platoExistente.actualizar(actualizarPlato) // pisa campo a campo los nuevos valores para la instancia existente
        platoExistente.validar()

        return platoRepository.update(platoExistente) // y aca se actualiza en el repo con el nuevo y lo devuelve
    }

    fun delete(id: Int): List<Plato> {
        val platoAEliminar = platoRepository.getById(id = id)
        platoRepository.delete(platoAEliminar)
        return platoRepository.findAll()
    }

    // Para asignar al local el plato nuevo
    fun asignarLocal(plato: Plato){
        // Mockeo el local, no se como asignarlo.
        // Infiero que debe de venir el id del usuario (cookies?, lo q hablamos sobre el login el martes) y buscarlo en el repo para asignarlo
        plato.local = Local(
            nombre = "Local False",
            direccion = Direccion("Calle falsa 123")
        )

        /*
        val nombreAsignatario = tareaActualizada.asignatario?.nombre
        // Solo llamamos a getAsignatario si el nombre contiene un valor distinto de null
        tareaActualizada.asignatario = nombreAsignatario?.let {
            usuariosRepository.getAsignatario(it) ?: throw NotFoundException("No se encontró el usuario <$it>")
        }
         */
    }

    // Agregar los ingredientes al palto, verificando con su repo que existan (nuevo/actualizar)
    fun asignarIngredientes(plato: Plato){
        val ingredientesActuales = plato.listaDeIngredientes.toMutableSet()
        plato.listaDeIngredientes.clear()

        // Filtro los ingredientes que existan en el repo, sino los guardo para mostrar en una lista los q no existen
        val ingredientesFaltantes = mutableSetOf<String>()
        ingredientesActuales.forEach { ingrediente ->
            val ingredienteExistente = ingredienteRepository.getByNombre(ingrediente.nombre)

            if (ingredienteExistente != null) {
                plato.agregarIngrediente(ingredienteExistente)
            } else {
                ingredientesFaltantes.add(ingrediente.nombre)
            }
        }

        if (ingredientesFaltantes.isNotEmpty()) {
            throw ErrorException.BusinessException("No se encontraron los ingredientes: ${ingredientesFaltantes.joinToString()}")
        }
    }
}