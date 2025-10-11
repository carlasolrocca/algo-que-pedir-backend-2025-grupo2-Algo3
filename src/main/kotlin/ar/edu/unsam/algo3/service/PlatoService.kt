package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.repositorios.Repositorio
import ar.edu.unsam.algo3.repositorios.Repositorios
import org.springframework.stereotype.Service

@Service
class PlatoService (
    private val repo: Repositorio<Plato>
) {
    fun getAll() = repo.findAll()

    fun getById(id: Int) = repo.getById(id)

    fun create(nuevoPlato: Plato): Plato {
        if (nuevoPlato.id != null) {
            throw ErrorException.BusinessException("No se debe pasar el id del plato")
        }
        nuevoPlato.validar()
        return repo.create(nuevoPlato)
    }

    fun update(id: Int, platoActualizado: Plato): Plato {
        // Validacion extra de URL (como en tareas)
        if (platoActualizado.id!! != id) {
            throw ErrorException.BusinessException("Id en URL distinto del id que viene en el body")
        }

        platoActualizado.validar()
        repo.update(platoActualizado)
        return platoActualizado
    }

    fun delete(id: Int): List<Plato> {
        val platoEliminado = repo.getById(id = id)
        repo.delete(platoEliminado)
        return repo.findAll()
    }
}