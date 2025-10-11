package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.repositorios.Repositorios
import org.springframework.stereotype.Service

@Service
class PlatoService () {
    val repo = Repositorios.plato

    fun getAll() = repo.findAll()

    fun getById(id: Int) = repo.getById(id)

    fun create(plato: Plato) = repo.create(plato)

    fun update(plato: Plato) = repo.update(plato)

    fun delete(plato: Plato) = repo.delete(plato)
}