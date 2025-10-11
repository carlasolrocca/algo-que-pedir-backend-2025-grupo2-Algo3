package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.repositorios.Repositorio
import ar.edu.unsam.algo3.ErrorException
import org.springframework.stereotype.Service

@Service
class PlatoService (
    private val platoRepository: Repositorio<Plato>,
    private val localRepository: Repositorio<Local>,
    private val ingredienteRepository: Repositorio<Ingrediente>
) {
    fun getAll() = platoRepository.findAll()

    fun getById(id: Int) = platoRepository.getById(id)

    fun create(nuevoPlato: Plato): Plato {
        if (nuevoPlato.id !== null) {
            throw ErrorException.BusinessException("No se debe pasar el identificador del plato")
        }
        asignarLocal(nuevoPlato)
        asignarIngredientes(nuevoPlato)
        nuevoPlato.validar()
        return platoRepository.create(nuevoPlato)
    }

    fun update(id: Int, actualizarPlato: Plato): Plato {
        // Validacion extra de URL (como en tareas)
        requireNotNull(actualizarPlato.id) { "Debe proveerse el ID del plato"} // esta validacion se hace en update del repo pero al metodo lo llamo al final de este... por eso repito validacion
        if (actualizarPlato.id!! != id) {
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

        // Falta validar que los ingredinetes esten correctos, PERO
        // si cada vez que quiero agregar un ingrediente al palto en el front
        // le pego al back p/ traer la lista de ingredientes que tiene...
        // entonces estaria OK el ingrediente y existe en el repo...?

        // Limpio la lista de ingredientes actual del plato y agrego los que son validos
        // No me convence, pero no se me ocurre otra forma de encararlo y fue la opcion mas rapida y optima q encontre
        // No me convence porque no se usan los metodos agregar y eliminar ingredientes, no seria esa la idea?
        plato.listaDeIngredientes.clear()
        ingredientesActuales.forEach {
            plato.listaDeIngredientes.add(it)
        }
    }
}