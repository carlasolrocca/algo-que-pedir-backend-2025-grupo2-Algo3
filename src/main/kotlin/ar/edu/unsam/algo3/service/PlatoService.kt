package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.repositorios.Repositorios
import org.springframework.stereotype.Service

@Service
class PlatoService () {
    val repo = Repositorios.plato

    fun getAll() = repo.findAll()

    fun getById(id: Int) = repo.getById(id)

    fun create(nuevoPlato: Plato): Plato {
        if (nuevoPlato.id != null) {
            throw ErrorException.BusinessException("No se debe pasar el id del plato")
        }
        nuevoPlato.validar()
        val platoGuardado = repo.create(nuevoPlato)
        return platoGuardado
    }

    fun update(id: Int, platoActualizado: Plato) {
        // Validacion extra de URL (como en tareas)
        if (platoActualizado.id!! != id) {
            throw ErrorException.BusinessException("Id en URL distinto del id que viene en el body")
        }

        return repo.update(plato)
    }

    fun delete(plato: Plato) = repo.delete(plato)
}