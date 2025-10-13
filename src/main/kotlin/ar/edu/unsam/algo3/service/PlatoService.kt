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
        if (nuevoPlato.id !== null) {
            throw ErrorException.BusinessException("No se debe pasar el identificador del plato")
        }
        // Evito tener platos duplicados, sino se generan dos veces
        if (platoIdentico(nuevoPlato)) {
            throw ErrorException.BusinessException("Ya existe un plato identico a ${nuevoPlato.nombre}")
        }

        asignarLocal(nuevoPlato)
        asignarIngredientes(nuevoPlato)
        nuevoPlato.validar()
        return platoRepository.create(nuevoPlato)
    }

    fun update(id: Int, actualizarPlato: Plato): Plato {
        //Es para que verifique primero que se haya ingresado ID antes de si es diferente
        //Me fallaba uno de los test si no hacía esto, pero probablemnte no es la solución ideal, quizá habría que dejar que ambas validaciones las haga el repo.
        if (actualizarPlato.id == null){
            throw ErrorException.BusinessException("El objeto debe tener un ID")
        }

        // Validacion extra de URL (como en tareas)
        if (actualizarPlato.id != id) {
            throw ErrorException.BusinessException("Id en URL distinto del id que viene en el body")
        }

        // deberia validar si se actualizo ingredientes, agregarlos y usar el repo de ingredientes
        val platoExistente = platoRepository.getById(id) // se recupera el plato actual del repo
        asignarIngredientes(platoExistente)
        platoExistente.actualizar(actualizarPlato) // pisa campo a campo los nuevos valores para la instancia existente
        platoExistente.validar()
        return platoRepository.update(actualizarPlato) // y aca se actualiza en el repo con el nuevo y lo devuelve
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

    // Para agregar los ingredientes con su repo al plato (nuevo/actualizar)
    fun asignarIngredientes(plato: Plato){
        val ingredientesActuales = plato.listaDeIngredientes.toMutableSet()
        plato.listaDeIngredientes.clear() // borro lo que habia para asignar solo los que son validos

        // Filtro los ingredientes que existan en el repo, sino los guardo para mostrar en una lista los q no existen
        val ingredinetesFaltantes = mutableListOf<String>()
        ingredientesActuales.forEach { ingrediente ->
            val ingredientesExistentes =
                ingredienteRepository.findAll().find { it.nombre.equals(ingrediente.nombre, ignoreCase = true) }
                //ingredienteRepository.getById(ingrediente.id!!)
            requireNotNull(ingredientesExistentes) { ingredinetesFaltantes.add(ingrediente.nombre) }
            plato.agregarIngrediente(ingredientesExistentes)
        }

        if (ingredinetesFaltantes.isNotEmpty()) {
            throw ErrorException.BusinessException("No se encontraron los ingredientes: ${ingredinetesFaltantes.joinToString()}")
        }
    }

    // Validacion. UN POCO ME HACE RUIDO peeeero...
    fun platoIdentico(plato: Plato): Boolean {
        val posiblesDuplicados = platoRepository.search(plato.nombre)
        return posiblesDuplicados.any { p ->
            p.local.nombre.equals(plato.local.nombre, ignoreCase = true) &&
            p.nombre.equals(plato.nombre, ignoreCase = true) &&
            p.descripcion.equals(plato.descripcion, ignoreCase = true) &&
            p.valorBase == plato.valorBase
        }
    }
}