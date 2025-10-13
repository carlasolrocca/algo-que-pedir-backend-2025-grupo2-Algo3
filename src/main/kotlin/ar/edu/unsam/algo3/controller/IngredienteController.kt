package ar.edu.unsam.algo3.ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.ar.edu.unsam.algo3.service.IngredienteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class IngredienteController(val ingredienteService: IngredienteService) {
    @GetMapping("/ingrediente")
    fun listarIngredientes(): List<Ingrediente> = ingredienteService.getAll()

    @GetMapping("/ingrediente/{id}")
    fun ingredientePorId(@PathVariable id: Int): Ingrediente = ingredienteService.getById(id)

    @PutMapping("/ingrediente/{id}")
    fun actualizarIngrediente(@PathVariable id: Int, @RequestBody ingredienteBody: Ingrediente): Ingrediente {
        return ingredienteService.update(id, ingredienteBody)
    }

    @DeleteMapping("/ingrediente/{id}")
    fun eliminar(@PathVariable id: Int): Ingrediente = ingredienteService.borrarIngrediente(id)

    @PostMapping("/ingrediente")
    fun crearIngrediente(@RequestBody ingredienteBody: Ingrediente): Ingrediente =
        ingredienteService.create(ingredienteBody)

}