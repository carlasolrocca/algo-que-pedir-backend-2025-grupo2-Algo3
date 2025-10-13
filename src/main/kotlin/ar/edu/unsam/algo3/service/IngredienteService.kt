package ar.edu.unsam.algo3.ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import org.springframework.stereotype.Service

@Service
class IngredienteService(private val repositorioIngrediente: IngredienteRepositorio) {

    fun getAll() = repositorioIngrediente.findAll()
    fun getById(id: Int) = repositorioIngrediente.getById(id)
    fun create(nuevoIngrediente: Ingrediente): Ingrediente {
        repositorioIngrediente.create(nuevoIngrediente)
        return nuevoIngrediente
    }
    fun update(id: Int, ingredienteActualizado: Ingrediente): Ingrediente {
        if (ingredienteActualizado.id == null) {
            throw ErrorException.BusinessException("Debe proveerse ID del ingrediente a actualizar")
        }
        if (ingredienteActualizado.id!! != id) {
            throw ErrorException.BusinessException("ID en URL distinto del ID en body")
        }
        val ingrediente = getById(id)
        ingrediente.actualizar(ingredienteActualizado)
        repositorioIngrediente.update(ingrediente)
        return ingrediente
    }
}