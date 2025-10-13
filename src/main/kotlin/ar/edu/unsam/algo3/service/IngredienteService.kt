package ar.edu.unsam.algo3.ar.edu.unsam.algo3.service

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
}